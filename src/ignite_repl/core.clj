(ns ignite_repl.core
  (:gen-class)
  (:require [nrepl.server :as nrepl-server]
            [cider.nrepl :refer (cider-nrepl-handler)]
            ;; [refactor-nrepl.middleware :refer (wrap-refactor)]
            ))

(defn -main []
  (nrepl-server/start-server
   :port 3333
   :handler (-> cider-nrepl-handler
                ;; wrap-refactor
                )))
