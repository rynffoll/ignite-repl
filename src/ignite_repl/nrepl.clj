(ns ignite_repl.nrepl
  (:gen-class)
  (:require [mount.core :refer [defstate]]
            [nrepl.server :refer [start-server stop-server]]
            [cider.nrepl :refer (cider-nrepl-handler)]
            [refactor-nrepl.middleware :refer (wrap-refactor)]
            [ignite_repl.config :refer [config]]))

(defstate nrepl
  :start (let [port (:nrepl/port config)]
           (start-server
            :bind "0.0.0.0"
            :port port
            :handler (-> cider-nrepl-handler
                         wrap-refactor)))
  :stop (stop-server nrepl))
