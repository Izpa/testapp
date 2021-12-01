(ns testapp.core
  (:gen-class)
  (:require [testapp.server :as server]))

(defn -main [& args]
  (server/start args))