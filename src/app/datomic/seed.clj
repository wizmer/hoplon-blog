(ns app.datomic.seed
  (:require
   [datomic.api :as d]
   [clj-time.core :refer [now]]
   [clojure.string :refer [lower-case]]))

(def blog-owner "Benoit Coste")

(defn create-schemas
  "Create a Datomic schema (simple example)"
  [conn]
  (let [user-schema [{:db/id                 #db/id[:db.part/db]
                      :db/ident              :person/first-name
                      :db/valueType          :db.type/string
                      :db/cardinality        :db.cardinality/one
                      :db/doc                "The first name of the person"
                      :db.install/_attribute :db.part/db}

                     {:db/id                 #db/id[:db.part/db]
                      :db/ident              :person/last-name
                      :db/valueType          :db.type/string
                      :db/cardinality        :db.cardinality/one
                      :db/doc                "The last name of the person"
                      :db.install/_attribute :db.part/db}

                     {:db/id                 #db/id[:db.part/db]
                      :db/ident              :person/email
                      :db/valueType          :db.type/string
                      :db/cardinality        :db.cardinality/one
                      :db/unique             :db.unique/value
                      :db/doc                "The email of the person"
                      :db.install/_attribute :db.part/db}]

        comment-schema [{:db/id                 #db/id[:db.part/db]
                         :db/ident              :comment/post-id
                         :db/valueType          :db.type/long
                         :db/cardinality        :db.cardinality/one
                         :db/unique             :db.unique/value
                         :db/doc                "The id of the post to which belongs this comment"
                         :db.install/_attribute :db.part/db}

                        {:db/id                 #db/id[:db.part/db]
                         :db/ident              :comment/user
                         :db/valueType          :db.type/string
                         :db/cardinality        :db.cardinality/one
                         :db/unique             :db.unique/value
                         :db/doc                "The comment's owner username"
                         :db.install/_attribute :db.part/db}

                        {:db/id                 #db/id[:db.part/db]
                         :db/ident              :comment/text
                         :db/valueType          :db.type/string
                         :db/cardinality        :db.cardinality/one
                         :db/doc                "The comment text"
                         :db.install/_attribute :db.part/db}]

        post-schema [{:db/id                 #db/id[:db.part/db]
                      :db/ident              :post/title
                      :db/valueType          :db.type/string
                      :db/cardinality        :db.cardinality/one
                      :db/unique             :db.unique/value
                      :db/doc                "The blog post title"
                      :db.install/_attribute :db.part/db}

                     {:db/id                 #db/id[:db.part/db]
                      :db/ident              :post/body
                      :db/valueType          :db.type/string
                      :db/cardinality        :db.cardinality/one
                      :db/doc                "The blog post body"
                      :db.install/_attribute :db.part/db}

                     {:db/id                 #db/id[:db.part/db]
                      :db/ident              :post/creation-user
                      :db/valueType          :db.type/string
                      :db/cardinality        :db.cardinality/one
                      :db/doc                "The user who created the post"
                      :db.install/_attribute :db.part/db}

                     {:db/id                 #db/id[:db.part/db]
                      :db/ident              :post/creation-datetime
                      :db/valueType          :db.type/instant
                      :db/cardinality        :db.cardinality/one
                      :db/doc                "The user who created the post"
                      :db.install/_attribute :db.part/db}

                     {:db/id                 #db/id[:db.part/db]
                      :db/ident              :post/edition-user
                      :db/valueType          :db.type/string
                      :db/cardinality        :db.cardinality/one
                      :db/doc                "The last user who edited the post"
                      :db.install/_attribute :db.part/db}

                     {:db/id                 #db/id[:db.part/db]
                      :db/ident              :post/edition-datetime
                      :db/valueType          :db.type/instant
                      :db/cardinality        :db.cardinality/one
                      :db/doc                "The last user who edited the post"
                      :db.install/_attribute :db.part/db}

                    {:db/id                 #db/id[:db.part/db]
                      :db/ident              :post/uri
                      :db/valueType          :db.type/string
                      :db/cardinality        :db.cardinality/one
                      :db/doc                "The post URI"
                      :db.install/_attribute :db.part/db}

                     {:db/id                 #db/id[:db.part/db]
                      :db/ident              :post/likes
                      :db/valueType          :db.type/long
                      :db/cardinality        :db.cardinality/one
                      :db/doc                "The number of likes"
                      :db.install/_attribute :db.part/db}]

        ]

    @(d/transact conn (concat post-schema
                              user-schema))))


(defn insert-data [conn data]
  (let [id (d/tempid :db.part/user)]
    (d/transact conn (conj [] (assoc data :db/id id)))
    id))

(defn insert-post-and-comments
  [conn title]
  (let [post-id (insert-data conn {:post/body  "Nullam eu ante vel est convallis dignissim.  Fusce suscipit, wisi nec facilisis facilisis, est dui fermentum leo, quis tempor ligula erat quis odio.  Nunc porta vulputate tellus.  Nunc rutrum turpis sed pede.  Sed bibendum.  Aliquam posuere.  Nunc aliquet, augue nec adipiscing interdum, lacus tellus malesuada massa, quis varius mi purus non odio.  Pellentesque condimentum, magna ut suscipit hendrerit, ipsum augue ornare nulla, non luctus diam neque sit amet urna.  Curabitur vulputate vestibulum lorem.  Fusce sagittis, libero non molestie mollis, magna orci ultrices dolor, at vulputate neque nulla lacinia eros.  Sed id ligula quis est convallis tempor.  Curabitur lacinia pulvinar nibh.  Nam a sapien."
                                   :post/creation-user "Benoit Coste"
                                   :post/creation-datetime (java.util.Date.)
                                   :post/title         title})]
    (insert-data conn {:comment/post-id post-id
                       :comment/user blog-owner
                       :comment/text "Here is some dummy content"})))
(defn seed-db [uri]
  (println "Filling DB with dummy blog posts...")
  (if-let [db (d/create-database uri)]
    (let [conn (d/connect uri)]
      (create-schemas conn)
      (doall (map #(insert-post-and-comments conn %) ["And so on..." "And so on" "A second blog post" "A first blog post"])))))


