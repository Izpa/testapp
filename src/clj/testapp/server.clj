(ns testapp.server
  (:require [environ.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]]
            [testapp.routes :as routes]))

(def main-frontend-js-app-path "/js/compiled/app.js")

(defn start [& [port]]
  (let [port (Integer. (or port (env :port) 8080))]
    (println "Start server...")
    (run-jetty (routes/all main-frontend-js-app-path) {:port port :join? false})))