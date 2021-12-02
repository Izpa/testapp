(defproject testapp "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :source-paths ["src/clj" "src/cljs" "src/cljc"]
  :resource-paths ["resources"]
  :target-path "target"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/core.async "1.4.627"]
                 [org.clojure/tools.cli "1.0.206"]
                 [nrepl "0.8.3"]

                 ;backend
                 [com.datomic/datomic-free "0.9.5697" :exclusions [com.google.guava/guava]]
                 [compojure "1.6.1"]
                 [environ "1.2.0"]
                 [ring/ring-core "1.9.4"]
                 [ring/ring-jetty-adapter "1.9.4"]
                 [hiccup "1.0.5"]
                 [metosin/muuntaja "0.6.8"]

                 ;js-app
                 [org.clojure/clojurescript "1.10.891"]
                 [com.cemerick/url "0.1.1"]
                 [cljsjs/react-dom "17.0.2-0"]
                 [day8.re-frame/async-flow-fx "0.3.0"]
                 [day8.re-frame/http-fx "0.2.3"]
                 [reagent "1.1.0"]
                 [re-frame "1.2.0"]
                 [thheller/shadow-cljs "2.16.2"]]
  :plugins [[lein-ring "0.12.5"]
            [lein-npm "0.6.2"]]
  :npm {:dependencies [[react "^17.0.2"]
                       [react-dom "^17.0.2"]]}
  :uberjar-name "testapp-standalone.jar"
  :main ^:skip-aot testapp.core
  :clean-targets ^{:protect false}
  [:target-path "resources/public/js/compiled"]
  :profiles {:dev     {:dependencies [[javax.servlet/servlet-api "2.5"]
                                      [ring/ring-mock "0.3.2"]]}
             :uberjar {:aot            :all
                       :prep-tasks ["compile"
                                    ["npm" "install"]
                                    ["run" "-m" "shadow.cljs.devtools.cli" "release" "app"]]
                       :resource-paths ["resources"]}})
