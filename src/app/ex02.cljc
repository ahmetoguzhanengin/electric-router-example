(ns app.ex02
  #?(:cljs (:import [goog.math Long]))
  (:require
    clojure.edn
    contrib.ednish
    [hyperfiddle.electric :as e]
    [hyperfiddle.electric-dom2 :as dom]
    [hyperfiddle.history :as history]))

;(e/defn AnaSayfa []
;        (e/client (dom/text "Ana Sayfa")
;                  (dom/div
;                    (history/link [::contact ["Ahmet" "Engin"]]
;                                  (dom/text "Hakkımızda sayfasına geçiş")))))

(e/defn AnaSayfa []
        (e/client
                  (dom/div
                      (dom/text "Bu sayfa electric kullanılarak hazırladığım ilk router sayfasıdır. Benim açımdan ilk başta anlamak çok zor oldu fakat şuanda güzel bir ilerleme kat ettiğimi düşünüyorum. "))

                  (dom/div
                    (dom/h4 (dom/text "Buraya bir motivasyon sözü bırakalım:")))



                  (dom/div
                    (dom/text "'Cehennem gibi çalışın. Demek istediğim, her hafta 80 ila 100 saatlik haftalar geçirmelisiniz. Bu başarı şansını artırır."))
                  (dom/div
                    (dom/text "Başka insanlar 40 saatlik bir çalışma haftası geçiriyorlarsa ve siz 100 saatlik çalışma haftası geçiriyorsanız, aynı şeyi yapıyor olsanız bile,"))
                  (dom/div
                    (dom/text "onların bir yılda ulaşacağı şeye 4 ay içinde ulaşacağınızı bilirsiniz.'"))

                  (dom/div
                    (dom/text "- Elon Musk -"))))





(e/defn Contact [[input1 input2]]
        (e/client (dom/text "Adınız: " input1 input2)
                  (dom/text " Hoş geldiniz!")))


(def read-edn-str (partial clojure.edn/read-string
                           {:readers #?(:cljs {'goog.math/Long goog.math.Long/fromString})})) ; datomic cloud long ids :clj {})}))


(e/defn Router [[page x]]
        (e/client
          (dom/h1 (dom/text "This is my first router example"))
          (dom/link (dom/props {:rel :stylesheet, :href "page.css"}))
          (dom/div (dom/text "Nav: ")
                   (history/link [::anasayfa] (dom/text "Ana Sayfa")) (dom/text " ")
                     (history/link [::contact ["Ahmet Oğuzhan  " "Engin"]] (dom/text "Contact")) (dom/text " ")))


        (case page
          ::anasayfa (history/router 1 (e/server (AnaSayfa.)))
          ::contact (history/router 2 (e/server (Contact. x)))
          (e/client (dom/text "böyle bir sayfa yok: " (pr-str page)))))



(e/defn app01 []
        (e/client
          (binding [dom/node js/document.body
                    history/encode contrib.ednish/encode-uri
                    history/decode #(or (contrib.ednish/decode-path % clojure.edn/read-string) [::anasayfa])]
            (history/router (history/HTML5-History.)
                            #_(set! (.-title js/document) (str (clojure.string/capitalize (name (first history/route))))
                                    " - Datomic Browser")
                            (dom/pre (dom/text (contrib.str/pprint-str history/route))

                              (Router. history/route))))))
