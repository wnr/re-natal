(defproject $PROJECT_NAME_HYPHENATED$ "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :license {:name "Eclipse Public License"
                      :url  "http://www.eclipse.org/legal/epl-v10.html"}
            :dependencies [[org.clojure/clojure "1.9.0"]
                           [org.clojure/clojurescript "1.10.238"]
                           $INTERFACE_DEPS$]
            :plugins [[lein-cljsbuild "1.1.4"]
                      [lein-figwheel "0.5.14"]]
            :clean-targets ["target/" #_($PLATFORM_CLEAN$)]
            :aliases {"prod-build" ^{:doc "Recompile code with prod profile."}
                                   ["do" "clean"
                                    ["with-profile" "prod" "cljsbuild" "once"]]
                      "advanced-build" ^{:doc "Recompile code for production using :advanced compilation."}
                                   ["do" "clean"
                                    ["with-profile" "advanced" "cljsbuild" "once"]]}
            :jvm-opts ["-XX:+IgnoreUnrecognizedVMOptions" "--add-modules=java.xml.bind"]
            :profiles {:dev {:dependencies [[figwheel-sidecar "0.5.14"]
                                            [com.cemerick/piggieback "0.2.1"]]
                             :source-paths ["src" "env/dev"]
                             :cljsbuild    {:builds [
#_($DEV_PROFILES$)]}
                             :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
                       :prod {:cljsbuild {:builds [
#_($PROD_PROFILES$)]}}
                       :advanced {:dependencies [[react-native-externs "0.1.0"]]
                                  :cljsbuild {:builds [
#_($ADVANCED_PROFILES$)]}}})
