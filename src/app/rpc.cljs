(ns app.rpc
  (:require-macros
   [javelin.core :refer [defc defc=]])
  (:require
   [javelin.core]
   [castra.core :refer [mkremote]]))

(defc state {})
(defc blog-post {})
(defc comments [])
(defc error nil)
(defc loading [])
(defc posts-list [])

(def get-list-of-posts
  (mkremote 'app.defrpc/get-list-of-posts posts-list error loading))

(def get-post
  (mkremote 'app.defrpc/get-record blog-post error loading))

(def get-comments
  (mkremote 'app.defrpc/get-comments comments error loading))

(def create-post
  (mkremote 'app.defrpc/create-post blog-post error loading))

(def get-random-user
  (mkremote 'app.defrpc/get-random-user state error loading))

(def get-record
  (mkremote 'app.defrpc/get-record state error loading))

(def update-user
  (mkremote 'app.defrpc/update-user state error loading))
