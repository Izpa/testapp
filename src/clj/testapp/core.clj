(ns testapp.core
  (:gen-class)
  (:require [clojure.tools.logging :as log]
            [testapp.server :as server]
            [testapp.db :as db]))

;; log uncaught exceptions in threads
(Thread/setDefaultUncaughtExceptionHandler
  (reify Thread$UncaughtExceptionHandler
    (uncaughtException [_ thread ex]
      (log/error {:what :uncaught-exception
                  :exception ex
                  :where (str "Uncaught exception on" (.getName thread))}))))

(defn -main [& args]
  (log/info "Migrate")
  (db/migrate)
  (log/info "Start server")
  (server/start args)
  (log/info "Done"))