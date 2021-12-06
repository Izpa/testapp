(ns testapp.req
  (:require [compojure.core :refer [context routes GET POST]]
            [datomic.api :as d]
            [integrant.core :as ig]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [muuntaja.middleware]))

(defn add [conn {:req/keys [title description declarer performer due-date]}]
  (let [temp-id "req"]
    (-> @(d/transact conn [{:db/id           temp-id
                            :req/title       title
                            :req/description description
                            :req/declarer    declarer
                            :req/performer   performer
                            :req/due-date    due-date}])
        :tempids
        (get temp-id))))

(defn all [conn from-id]
  (->> (d/q '[:find (pull ?e [*])
              :where [?e :req/title]]
            (d/db conn))
       flatten
       (sort-by :db/id)
       reverse
       (take-while #(-> % :db/id (> (or from-id 0))))
       vec))

(defn- all-handler [req-service request]
  (let [from-id (-> request
                    :params
                    :from-id
                    (#(re-find #"\d+" %))
                    (or "0")
                    BigInteger.)]
    {:status  200
     :headers {"content-type" "application/edn"}
     :body    (-> req-service
                  :db-connection
                  (all from-id))}))

(defn- add-handler [req-service request]
  (let [req (:body-params request)]
    {:status  200
     :headers {"content-type" "application/edn"}
     :body    (assoc req :db/id (-> req-service
                                    :db-connection
                                    (add req)))}))

(defmethod ig/init-key :testapp.req/service
  [_ {:keys [db]}]
  {:db-connection (:connection db)})

(defmethod ig/init-key :testapp.req/endpoints
  [_ {:keys [req-service]}]
  (-> (routes
        (context "/req" []
          (GET "/all" request (all-handler req-service request))
          (POST "/add" request (add-handler req-service request))))
      wrap-params
      wrap-keyword-params
      muuntaja.middleware/wrap-format
      muuntaja.middleware/wrap-params))