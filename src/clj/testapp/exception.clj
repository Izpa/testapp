(ns testapp.exception
  (:require [clojure.tools.logging :as logging])
  (:import java.lang.Thread$UncaughtExceptionHandler))

(defn report! [ex message]
  (logging/error ex message))

(defn set-default-uncaught-exception-handler []
  (Thread/setDefaultUncaughtExceptionHandler
   (reify Thread$UncaughtExceptionHandler
     (uncaughtException [_ _ ex]
       (report! ex "uncaught exception")))))
