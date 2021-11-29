(ns testapp.response
  (:require [hiccup.page :as page]))

(defn- index-html [js-output-name]
  (page/html5
    {:lang "en"}
    [:head
     [:meta {:charset "UTF-8"}]
     [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
     [:link {:rel "icon" :href "/public/favicon.ico"}]]
    [:body
     [:div#app]
     (page/include-js js-output-name)]))

(defn index [_ js-output-name]
  {:status 200
   :headers {"contenаt-type" "text/plain"}
   :body (index-html js-output-name)})

(defn health [_]
  {:status  200
   :headers {"content-type" "text/plain"}
   :body    "OK"})
