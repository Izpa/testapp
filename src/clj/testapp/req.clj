(ns testapp.req
  (:require [testapp.db :refer [all-req add-req->req-id]]))

(defn all [from-id]
  {:status  200
   :headers {"content-type" "application/edn"}
   :body    (all-req from-id)})

(defn add [req]
  {:status  200
   :headers {"content-type" "application/edn"}
   :body    (assoc req :db/id (add-req->req-id req))})