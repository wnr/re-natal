                                                     {:id           "$PLATFORM$"
                                                      :source-paths ["src" "env/dev"]
                                                      :figwheel     true
                                                      :compiler     {:output-to     "target/$PLATFORM$/index.js"
                                                                     :main          "env.$PLATFORM$.main"
                                                                     :output-dir    "target/$PLATFORM$"
                                                                     :optimizations :none
                                                                     :target :nodejs}}