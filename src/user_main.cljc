(ns user-main
  (:require contrib.ednish
            [hyperfiddle.api :as hf]
            [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.history :as history]

            app.demo-index
            app.ex02))

;;Sayfa bulunamama durumu ile ilgili tanımlamalar.
;Bu bölümde NotFoundPage adlı bir fonksiyon tanımlanıyor. Bu fonksiyon, bir HTML sayfasının içeriğini temsil eden ClojureScript kodu içeriyor.
;Sayfanın içeriği, bir başlık (h1) ve metin ("Page not found!") içeren bir DOM elemanıdır.
(e/defn NotFoundPage []
        (e/client (dom/h1 (dom/text "Page not found!"))))

;Sayfa  tanımlaması
;Bu bölümde Pages adlı bir fonksiyon tanımlanıyor. Bu fonksiyon, bir sayfa adını parametre olarak alır ve bu sayfa adına göre ilgili sayfayı oluşturan fonksiyonu çağırır.
;Eğer sayfa adı 'app.demo-index/Demos' ise app.demo-index/Demos fonksiyonunu çağırır, eğer sayfa adı 'app.t01/app01' ise app.t01/app01 fonksiyonunu çağırır. Eğer belirtilen sayfa adına karşılık gelen bir fonksiyon bulunamazsa, NotFoundPage fonksiyonunu çağırır.
(e/defn Pages [page]
        (e/server
          (case page
            `app.demo-index/Demos app.demo-index/Demos
            `app.ex02/app01 app.ex02/app01
             NotFoundPage)))


(e/defn Main []
        (binding [history/encode contrib.ednish/endcode-uri
                  history-decode #(or (contrib.endish/decode-path % hf/read-edn-str)
                                      [`app.demo-index/Demos])]
          (histort/router (history/HTML5-History.)
                          (set! (.-title js/document) (str (clojure.string/capitalize (name (first history/route))) " - Hyperfiddle"))
                          (binding [dom/node js/document.body])
                          (dom/pre (dom/text (contrib.str/pprint-str history/route)))
                          (let [[page & args] history/route]
                            (e/server (new (Pages. page #_args)))))))


;Bu bölümde Main adlı bir fonksiyon tanımlanıyor. Bu fonksiyon, sayfa geçmişi yönetimi ve yönlendirme işlemlerini içerir.
;history/router fonksiyonu, HTML5 sayfa geçmişi nesnesi ile birleştirilir ve sayfa geçmişi değişiklikleri dinlenir.
;Sayfa başlığı güncellenir ve Pages fonksiyonu çağrılarak ilgili sayfa içeriği oluşturulur.



;;binding Bloğu:
;(binding [history/encode contrib.ednish/encode-uri
;          history/decode #(or (contrib.ednish/decode-path % hf/read-edn-str)
;                              [`app.demo-index/Demos])]

;binding bloğu, belirli dinamik değişkenlere geçici bir değer atamak için kullanılır.
;Bu örnekte, history/encode ve history/decode değişkenleri, contrib.ednish/encode-uri ve bir anonymous (anonim) fonksiyon ile atanır.
;Bu fonksiyon, bir diziyi alır ve eğer dizinin decode işlemi başarısız olursa, varsayılan olarak app.demo-index/Demos sayfasını kullanır.


;history/router Fonksiyonu:
;(history/router (history/HTML5-History.)
;                (set! (.-title js/document) (str (clojure.string/capitalize (name (first history/route))) " - Hyperfiddle"))
;                (binding [dom/node js/document.body])
;                (dom/pre (dom/text (contrib.str/pprint-str history/route)))
;                (let [[page & args] history/route]
;                  (e/server (new (Pages. page #_args)))))

;Bu satır, sayfa geçmişi yönetimi ve yönlendirme işlemlerini gerçekleştirir.
; history/HTML5-History. ile bir HTML5 sayfa geçmişi nesnesi oluşturulur.
; Ardından, set! fonksiyonu ile sayfa başlığı güncellenir. dom/node değişkeni, js/document.body ile eşleştirilir,
; böylece sayfanın gövdesine erişim sağlanır. dom/pre fonksiyonu ile bir <pre> elemanı oluşturulur ve bu elemanın içeriği, sayfa geçmişi (history/route) bilgisinin gösterimi olur.

;let Bloğu:
;(let [[page & args] history/route]
;  (e/server (new (Pages. page #_args))))

;let bloğu, yerel değişkenleri tanımlamak ve kullanmak için kullanılır.
; history/route üzerinden sayfa ve argümanlar alınır. Bu bilgiler kullanılarak Pages fonksiyonu çağrılır ve ilgili sayfa içeriği oluşturulur. e/server fonksiyonu, sayfa içeriğini sunucu (server) tarafında oluşturur.


