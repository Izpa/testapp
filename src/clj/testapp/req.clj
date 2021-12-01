(ns testapp.req
  (:require [testapp.db :refer [list-req add-req->req-id]]))

(defn list []
  (list-req))

(defn add [req]
  (assoc req :db/id (add-req->req-id req)))