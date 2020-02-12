(ns ignite_repl.core
  (:gen-class)
  (:require [mount.core :refer [start]]
            [ignite_repl.config :refer [config]]
            [ignite_repl.nrepl :refer [nrepl]]
            [clojure.pprint :refer [pprint]]))

(defn -main []
  (start #'config)
  (pprint config)
  (start #'nrepl))
