(ns testapp.routes
  (:require [compojure.core :refer [GET POST routes]]
            [compojure.route :as route]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [muuntaja.middleware]
            [testapp.response :as response]
            [testapp.req :as req]))

(defn all [main-frontend-js-app-path]

  (-> (routes
        (GET "/" _ (response/index main-frontend-js-app-path))
        (GET "/testapp" _ (response/index main-frontend-js-app-path))
        (GET "/health" _ (response/health))

        (GET "/test" request {:status  200
                               :headers {"content-type" "application/edn"}
                               :body    {:req (-> request
                                                  :params
                                                  (get "from-id")
                                                  (#(re-find  #"\d+" %))
                                                  (or "0")
                                                  BigInteger.)}})

        (GET "/req/all" request (req/all (-> request
                                             :params
                                             (get "from-id")
                                             (#(re-find  #"\d+" %))
                                             (or "0")
                                             BigInteger.)))
        (POST "/req/add" request (-> request
                                     :body-params
                                     req/add))
        (route/resources "/")
        (route/not-found "Page not found."))
      wrap-params
      wrap-keyword-params
      muuntaja.middleware/wrap-format
      muuntaja.middleware/wrap-params))