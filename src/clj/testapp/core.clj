(ns testapp.core
  (:gen-class)
  (:require [compojure.core :refer [GET routes]]
            [compojure.route :as route]
            [environ.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]]
            [testapp.response :as response]))

(defn make-routes [main-frontend-js-app-path]
  (routes
    (GET "/" request (response/index request main-frontend-js-app-path))
    (GET "/app/*path" request (response/index request main-frontend-js-app-path))
    (GET "/health" request (response/health request))
    (route/resources "/")
    (route/not-found "Page not found.")))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 8080))]
    (println "Start server...")
    (run-jetty (make-routes "/js/compiled/app.js") {:port port :join? false})))