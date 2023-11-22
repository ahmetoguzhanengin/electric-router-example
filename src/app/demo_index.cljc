(ns ;^:dev/always
  app.demo-index
  (:require [hyperfiddle.electric :as e]
            [hyperfiddle.electric-dom2 :as dom]
            [hyperfiddle.history :as router]))



(def pages
  [`app.ex02/app01])

;Bu bölümde, pages adlı bir değişken tanımlanıyor ve bir vektör içinde tek bir öğe olan bir ClojureScript ismi (app.t01/app01) içeriyor.
;Bu isim, muhtemelen sayfaların adını temsil eder.
(e/defn Demos []
        (e/client
          (e/for [k pages]
                 (dom/div (router/link [k] (dom/text (name k)))))))

;Bu bölümde, Demos adlı bir fonksiyon tanımlanıyor. Bu fonksiyon, sayfa içeriğini oluşturur. e/client fonksiyonu, sayfanın istemci (client) tarafında oluşturulacağını belirtir. e/for fonksiyonu, belirli bir koleksiyon üzerinde döngü yapmak için kullanılır. Bu örnekte, pages vektörü üzerinde dönülüyor.
;
;Her bir öğe için ([k pages]), bir dom/div elemanı oluşturuluyor. Bu elemanın içeriği, router/link fonksiyonu tarafından sağlanan bir bağlantıdır. router/link fonksiyonu, belirli bir sayfaya yönlendiren bir bağlantı oluşturur. Bu sayfanın adı, vektör olarak ([k]) verilir ve içeriği bir metin elemanı (dom/text) ile temsil edilir.
;
;Bu kod, muhtemelen birkaç demo sayfasının listelendiği bir sayfa oluşturmayı amaçlamaktadır. app.t01/app01 adlı sayfanın bağlantısı, bu sayfa ismini gösteren bir <div> elemanı içinde bulunacaktır.





