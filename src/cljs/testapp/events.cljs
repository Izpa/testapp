(ns testapp.events
  (:require
    [ajax.edn]
    [re-frame.core :as re-frame]))

(re-frame/reg-event-db
  :initialise-db
  (fn [_ _]
    {:reqs []}))

(re-frame/reg-event-fx
  ::add-req
  (fn [_ [_ req]]
    {:http-xhrio {:method          :post
                  :uri             "/req/add"
                  :params          req
                  :timeout         5000
                  :format          (ajax.edn/edn-request-format)
                  :response-format (ajax.edn/edn-response-format)
                  :on-success [::get-reqs]}}))

(re-frame/reg-event-fx
  ::get-reqs
  (fn [{{reqs :reqs} :db} _]
    {:http-xhrio {:method          :get
                  :uri             "/req/all"
                  :timeout         5000
                  :params          {:from-id (-> reqs first :db/id)}
                  :response-format (ajax.edn/edn-response-format)
                  :on-success [::receive-reqs]}}))

(re-frame/reg-event-db
  ::receive-reqs
  (fn [db [_ reqs]]
    (update db :reqs #(-> reqs (into %) vec))))