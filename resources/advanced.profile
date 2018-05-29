                                                   {:id           "$PLATFORM$"
                                                    :source-paths ["src" "env/prod"]
                                                    :compiler     {:output-to     "index.$PLATFORM$.js"
                                                                   :main          "env.$PLATFORM$.main"
                                                                   :output-dir    "target/$PLATFORM$"
                                                                   :static-fns    true
                                                                   :optimize-constants true
                                                                   :optimizations :advanced
                                                                   :target :nodejs
                                                                   :closure-defines {"goog.DEBUG" false}}}