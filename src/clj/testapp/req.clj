(ns testapp.req
  (:require [testapp.db :refer [all-req add-req->req-id]]))

(defn- all [from-id]
  (all-req from-id))

(defn- add [req]
  (assoc req :db/id (add-req->req-id req)))

(defn all-handler [request respond raise]
  (let [from-id (-> request
                    :params
                    (get "from-id")
                    (#(re-find #"\d+" %))
                    (or "0")
                    BigInteger.)]
    (respond {:status  200
              :headers {"content-type" "application/edn"}
              :body    (all from-id)})))

(defn add-handler [request respond raise]
  (let [req (:body-params request)]
    (respond {:status  200
              :headers {"content-type" "application/edn"}
              :body    (add req)})))