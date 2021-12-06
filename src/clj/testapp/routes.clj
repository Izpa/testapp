(ns testapp.routes
  (:require [compojure.core :refer [GET POST routes]]
            [compojure.route :as route]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [muuntaja.middleware]
            [testapp.response :as response]))

(defn main-routes [main-frontend-js-app-path]
  [(GET "/" _ (response/index main-frontend-js-app-path))
   (GET "/testapp" _ (response/index main-frontend-js-app-path))
   (GET "/health" _ (response/health))
   (route/resources "/")
   (route/not-found "Page not found.")])

(defn dry-app [endpoints main-frontend-js-app-path]
  (->> main-frontend-js-app-path
       main-routes
       (concat endpoints)
       (apply routes)))

(defn app [endpoints main-frontend-js-app-path]
  (-> (dry-app endpoints main-frontend-js-app-path)))