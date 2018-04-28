(ns reagent-blog.app
  (:require [reagent-blog.frame :as frame]
            [reagent.core :as reagent :refer [atom]]))


(defn introduction-container
  []
  [:div
   [:h1 {:class "mt-4"} [:img {:class "img-fluid rounded" :src "https://hoplon.io/images/logos/hoplon-logo.png" :alt "The hoplon logo" :width "100" :height "100"}] "A hoplon blog template"]
   [:p {:class "lead"}
    "This is an attempt at a ClojureScript-powered blog based on the " [:a {:href "https://hoplon.io"} "Hoplon"] " library. The database on the server side is powered by " [:a :href "https://datomic.com" "Datomic"]]
   [:p "You are currently on the HOME page where you can see the list of blog posts."]
   [:p "Go to the ADMIN page to create new posts."]
   [:hr]
   ])

(defn blog-container
 [_ _]
  [frame/layout
   [:div {:class "container"}
    [:div {:class "row"}
     [:div {:class "col-lg-8"}
      [introduction-container]
      [:h1 {:class "mt-4"} "List of blog posts"]
      ;; [ul [for-tpl [item posts-list] [li [:a :href [str "article.html#" [@item 0]] [@item 1] ]]]]
      ]]]])


(defn init []
  (reagent/render-component [blog-container]
                            (.getElementById js/document "container")))
