(ns testapp.routes
  (:require [compojure.core :refer [GET POST routes]]
            [compojure.route :as route]
            [testapp.response :as response]
            [testapp.req :as req]))

(defn all [main-frontend-js-app-path]
  (routes
    (GET "/" _ (response/index _ main-frontend-js-app-path))
    (GET "/app/*path" _ (response/index _ main-frontend-js-app-path))
    (GET "/health" _ (response/health _))
    (GET "/req" _ (req/list))
    (POST "/req" request (print request))
    (route/resources "/")
    (route/not-found "Page not found.")))