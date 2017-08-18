(ns app.rpc
  (:require-macros
   [javelin.core :refer [defc defc=]])
  (:require
   [javelin.core]
   [castra.core :refer [mkremote]]))

(defc state {})
(defc error nil)
(defc loading [])

(def get-random-user
  (mkremote 'app.api/get-random-user state error loading))

(def get-user
  (mkremote 'app.api/get-user state error loading))

(def update-user
  (mkremote 'app.api/update-user state error loading))
