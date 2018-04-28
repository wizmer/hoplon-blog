(ns reagent-blog.app
  (:require [reagent-blog.frame :as frame]
            [reagent.core :as reagent :refer [atom]]))


(defelem new-post-form
  [_ _]
  (println "building defelem")
  (let [post-body (cell "")
        post-title (cell "")
        user (cell "")]
    (div :class "card my-4"
       (h5 :class "card-header" "New post")
       (div :class "card-body")
       (form
        (form-group (label :for "post-title" "Title: ")
         (input :class "form-control"
                   :id "post-title"
                   :type "text"
                   :value post-title
                   :keyup #(reset! post-title @%)))

        (form-group (label :for "post-body" "body: ")
                    (textarea :class "form-control" :rows "3"
                           :id "post-body"
                           :type "text"
                           :value post-body
                           :keyup #(reset! post-body @%)))

        (button :click #(dosync
                         (println "New post published !")
                         (rpc/create-post {:post/title @post-title
                                           :post/body @post-body
                                           :post/creation-user "Janno"})
                         (reset! post-title "")
                         (reset! post-body "")
                         (reset! user ""))
                :type "submit" :class "btn btn-primary" "Publish")))))

(defelem admin-container
  [_ _]
  (div :class "container"
       (div :class "row"
            (div :class "col-lg-8"
                 (p (text "Random Datomic data ~{posts}"))
                 (new-post-form)))))


(defn init []
  (reagent/render-component [blog-container]
                            (.getElementById js/document "container")))
