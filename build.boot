(set-env!
  :dependencies '[[adzerk/boot-cljs          "1.7.228-2"]
                  [adzerk/boot-reload        "0.4.13"]
                  [adzerk/boot-cljs-repl "0.3.0"]       ;; add bREPL
                  [com.cemerick/piggieback "0.2.1" :scope "test"]
                  [weasel "0.7.0" :scope "test"]
                  [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                  [com.andrewmcveigh/cljs-time "0.5.0"]
                  [hoplon/hoplon             "6.0.0-alpha17"]
                  [org.clojure/clojure       "1.8.0"]
                  [org.clojure/clojurescript "1.9.293"]
                  [environ                   "1.0.1"]
                  [mount                     "0.1.7"]
                  [pandeiro/boot-http        "0.7.3"]
                  [hoplon/castra             "3.0.0-alpha4"]
                  [compojure                 "1.6.0-beta1"]
                  [ring                      "1.5.0"]
                  [javax.servlet/servlet-api "2.5"]
                  [com.datomic/datomic-free  "0.9.5344"]
                  [ring/ring-defaults        "0.2.1"]]
  :source-paths #{"src"}
  :resource-paths  #{"assets"})

(require
  '[adzerk.boot-cljs         :refer [cljs]]
  '[adzerk.boot-reload       :refer [reload]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
  '[hoplon.boot-hoplon       :refer [hoplon prerender]]
  '[pandeiro.boot-http    :refer [serve]])

(deftask dev
  "Build ws-simple for local development."
  []
  (comp
   (serve
    :init 'app.datomic.db/init
    :handler 'app.handler/app
    :reload true
    :port 8000)
   (watch)
   (speak)
   (hoplon)
   (reload)
   (cljs-repl)
   (cljs)))

(deftask prod
  "Build blog for production deployment."
  []
  (comp
    (hoplon)
    (cljs :optimizations :advanced)
    (target :dir #{"target"})))
