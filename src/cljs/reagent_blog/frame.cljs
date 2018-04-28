(ns reagent-blog.frame
  (:require [reagent.core :as reagent :refer [atom]]))

(defn navbar
  [children]
  [:nav {:class "navbar navbar-expand-lg navbar-dark bg-dark fixed-top"}
   [:a {:class "navbar-brand" :href "#"} "My fancy blog name"]
   [:button {:class "navbar-toggler" :type "button" :data-toggle "collapse" :data-target "#navbarResponsive" :aria-controls "navbarResponsive" :aria-expanded "false" :aria-label "Toggle navigation"}
    [:span {:class "navbar-toggler-icon"}]]
   [:div {:id "navbarResponsive" :class "collapse navbar-collapse"}
             [:ul {:class "navbar-nav ml-auto"}
              (for [child children]
                ^{:key (child :title)} [:li {:class "nav-item"}
                 [:a {:class "nav-link" :href (child :href)} (child :title)]])]
    ]])

(defn layout
  [child]
  [:div
   [navbar [{:title "Home" :href "index.html"} {:title "Admin" :href "admin.html"}]]
   child
   [:footer {:class "py-5 bg-dark"}
    [:p {:class "m-0 text-center text-white"} "A hoplon-powered blog template inspired from: "
     [:a {:href "https://github.com/BlackrockDigital/startbootstrap-blog-post"}
      "https://github.com/BlackrockDigital/startbootstrap-blog-post"]]

     [:p {:class "m-0 text-center text-white"}
      [:a { :href "#"} "Back to top"]]

    ]])
