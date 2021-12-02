(ns testapp.events
  (:require
    [ajax.edn]
    [cemerick.url :refer [url]]
    [re-frame.core :as re-frame]))

(re-frame/reg-event-db
  :initialise-db
  (fn [_ _]
    {:reqs (sorted-map)}))

(def base-url (str (url (-> js/window .-location .-href) "/")))

(re-frame/reg-event-fx
  ::add-req
  (fn [_world [_ req]]
    {:http-xhrio {:method          :post
                  :uri             (str base-url "req/add" )
                  :params          req
                  :timeout         5000
                  :format          (ajax.edn/edn-request-format)
                  :response-format (ajax.edn/edn-response-format)}}))
