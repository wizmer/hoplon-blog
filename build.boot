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
                  [tailrecursion/boot-jetty  "0.1.3"]]
  :source-paths #{"src"}
  :asset-paths  #{"assets"})

(require
  '[adzerk.boot-cljs         :refer [cljs]]
  '[adzerk.boot-reload       :refer [reload]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
  '[hoplon.boot-hoplon       :refer [hoplon prerender]]
  '[tailrecursion.boot-jetty :refer [serve]])

(deftask dev
  "Build blog for local development."
  []
  (comp
    (watch)
    (speak :theme "woodblock")
    (hoplon)
    (reload)
    ;; (cljs-repl)
    (cljs)
    (serve :port 8000)))

(deftask prod
  "Build blog for production deployment."
  []
  (comp
    (hoplon)
    (cljs :optimizations :advanced)
    (target :dir #{"target"})))
