(ns app.ex01
  #?(:cljs (:import [goog.math Long]))
  (:require
    clojure.edn
    contrib.ednish
    [hyperfiddle.electric :as e]
    [hyperfiddle.electric-dom2 :as dom]
    [hyperfiddle.history :as history]))

(e/defn Test1 []
        (e/client (dom/text "Test1 Page")
                  (dom/div
                    (history/link [::test2 ["Input-1" "Input-2"]]
                                  (dom/text "Got to Test2 page")))))

;history/link fonksiyonu, sayfa geçişlerini yöneten bir bağlantı oluşturur. Bu bağlantı, [::test2 ["Input-1" "Input-2"]] vektörü tarafından temsil edilir. Bu vektör, geçilecek sayfanın adını ve sayfaya gönderilecek argümanları içerir. Ardından, bağlantının içeriği bir metin elemanı (dom/text) ile "Go to Test2 page" olarak belirlenir.

(e/defn Test2 [[input1 input2]]
        (e/client (dom/text "Test2 with parameters: " input1 input2)))

;e/client fonksiyonu, sayfanın istemci (client) tarafında oluşturulacağını belirtir. İçeriği, dom/text fonksiyonu ile bir metin elemanı oluşturularak başlar. Bu metin elemanının içeriği, "Test2 with parameters: " ifadesi ve input1 ve input2 değerleri ile devam eder.


(def read-edn-str (partial clojure.edn/read-string
                           {:readers #?(:cljs {'goog.math/Long goog.math.Long/fromString})})) ; datomic cloud long ids :clj {})}))

;partial fonksiyonu, bir fonksiyonu ve bazı argümanları alarak yeni bir fonksiyon oluşturur. Bu durumda, clojure.edn/read-string fonksiyonu kısmen uygulanıyor ve yeni bir fonksiyon elde ediliyor.

(e/defn Page [[page x]]
        (e/client
          (dom/h1 (dom/text "Routing Template"))
          (dom/link (dom/props {:rel :stylesheet, :href "gridsheet-optional.css"}))
          (dom/div (dom/text "Nav: ")
                   (history/link [::summary] (dom/text "home")) (dom/text " ")
                   (history/link [::test2 ["Input-1" "Input-2"]] (dom/text "test2")) (dom/text " ")))



        (case page
          ::summary (history/router 1 (e/server (Test1.)))
          ::test2 (history/router 2 (e/server (Test2. x)))
          (e/client (dom/text "no matching route: " (pr-str page)))))





(e/defn app01 []
        (e/client
          (binding [dom/node js/document.body
                    history/encode contrib.ednish/encode-uri
                    history/decode #(or (contrib.ednish/decode-path % clojure.edn/read-string) [::summary])]
            (history/router (history/HTML5-History.)
                            #_(set! (.-title js/document) (str (clojure.string/capitalize (name (first history/route))))
                                    " - Datomic Browser")
                            (dom/pre (dom/text (contrib.str/pprint-str history/route))

                              (PageDeneme. history/route))))))
