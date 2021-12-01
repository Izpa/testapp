(ns testapp.db
  (:require [datomic.api :as d]))

(def db-uri "datomic:mem://testapp")

(def create-db []
  (d/create-database db-uri))

(def conn (d/connect db-uri))

(def req-schema [{:db/ident       :req/title
                  :db/valueType   :db.type/string
                  :db/cardinality :db.cardinality/one
                  :db/doc         "The title of req"}

                 {:db/ident       :req/description
                  :db/valueType   :db.type/string
                  :db/cardinality :db.cardinality/one
                  :db/doc         "The description of req"}

                 {:db/ident       :req/declarer
                  :db/valueType   :db.type/string
                  :db/cardinality :db.cardinality/one
                  :db/doc         "The declarer of req"}

                 {:db/ident       :req/performer
                  :db/valueType   :db.type/string
                  :db/cardinality :db.cardinality/one
                  :db/doc         "The performer of req"}

                 {:db/ident       :req/due-date
                  :db/valueType   :db.type/string
                  :db/cardinality :db.cardinality/one
                  :db/doc         "The due date of req"}])

(defn migrate []
  (d/transact conn req-schema))

(defn add-req [title description declarer performer due-date]
  (d/transact conn [{:req/title       title
                     :req/description description
                     :req/declarer    declarer
                     :req/performer   performer
                     :req/due-date    due-date}]))

(defn add-req [title description declarer performer due-date]
  (d/transact conn [{:req/title       title
                     :req/description description
                     :req/declarer    declarer
                     :req/performer   performer
                     :req/due-date    due-date}]))

(defn add-req->req-id [{:req/keys [title description declarer performer due-date]}]
  (let [temp-id "req"]
    (-> @(d/transact conn [{:db/id           temp-id
                            :req/title       title
                            :req/description description
                            :req/declarer    declarer
                            :req/performer   performer
                            :req/due-date    due-date}])
        :tempids
        (get temp-id))))

(defn list-req []
  (->> (d/q '[:find (pull ?e [*])
              :where [?e :req/title]]
            (d/db conn))
       flatten
       (sort-by :db/id)
       vec))