(ns testapp.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [ring.adapter.jetty :refer [run-jetty]]
            [testapp.routes :refer [app]]
            [testapp.exception :as exception]
            [datomic.api :as d]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.tools.cli :refer [parse-opts]]
            [integrant.core :as ig]))


(defmethod ig/init-key :testapp/server
  [_ {:keys [endpoints main-frontend-js-app-path] :as options}]
  (run-jetty (app endpoints main-frontend-js-app-path) options))

(defmethod ig/init-key :testapp/db
  [_ {:keys [uri schema]}]
  (d/create-database uri)
  (let [db-connection (d/connect uri)]
    (d/transact db-connection schema)
    {:connection db-connection}))

(def system nil)

(defn -main []
  (let [config (-> "config.edn" io/resource slurp ig/read-string)]
    (exception/set-default-uncaught-exception-handler)
    (try
      (ig/load-namespaces config)
      (alter-var-root #'system (fn [_] (ig/init config)))
      (catch Exception ex
        (if-let [cause (ex-cause ex)]
          (throw cause)
          (throw ex))))
    (println "system started")))