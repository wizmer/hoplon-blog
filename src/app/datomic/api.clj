(ns app.datomic.api
  (:require
    [app.datomic.db :refer [datomic-conn]]
    [datomic.api :as d]))


(defn- fetch-ids
   "Find all the IDs in the database"
   []
   (d/q '[:find ?e :where [?e :person/first-name]] (d/db datomic-conn)))

(defn- random-id
  "Choose a random ID from the database"
  []
  (rand-nth (flatten (map identity (fetch-ids)))))

(defn fetch-comments
  "Use the pull API to fetch all attributes for a random ID"
  [id]
  (println "Fetching comments")
  {})

(defn fetch-record
  "Use the pull API to fetch all attributes for a random ID"
  [id]
  (println "Fetching comments")
  (let [result (d/q '[:find ?e ?title :where [?e :post/title ?title]] (d/db datomic-conn))]
    (println "Resulting in: " result)
    result))


(defn update-record!
  "Update the record and change a property on the server to show that we also did something!"
  [user-data]
  (let [email (str (clojure.string/lower-case (:person/first-name user-data)) "."
                   (clojure.string/lower-case (:person/last-name user-data)) "@email-server.com")
    data (assoc user-data :person/email email)
    noop (println "Updating with " data)]
   @(d/transact datomic-conn (conj [] data))))

(defn create-post! [data]
  (let [_ (println "creating new post: " data)]
    @(d/transact datomic-conn (conj [] (assoc data :db/id (d/tempid :db.part/user))))))

(defn fetch-posts-list
  "Get a list of all post titles"
  []
  (println "Fetching all post titles")
  (let [result (d/q '[:find ?e ?title :where [?e :post/title ?title]] (d/db datomic-conn))]
    (println "Returning: " result)
    result))



