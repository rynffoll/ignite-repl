(ns user
  (:require [mount.core :as mount]
            [ignite_repl.config :refer [config]]
            [ignite_repl.ignite :refer [ignite] :as ig]
            [ignite_repl.cluster :as cluster]
            [ignite_repl.cache :as cache]
            [ignite_repl.print :as print]))

(comment

  ;; http://insideclojure.org/2018/05/04/add-lib/
  (require '[clojure.tools.deps.alpha.repl :refer [add-lib]])

  (add-lib 'aero {:mvn/version "1.1.3"})

  (mount/start #'config)

  (mount/stop #'config)

  config

  (ig/state?)

  (mount/start #'ignite)

  (mount/stop #'ignite)

  (cluster/activated? ignite)

  (cluster/activate! ignite)

  (cluster/deactivate! ignite)

  (cluster/topology ignite)

  (cluster/baseline ignite)
  
  (cluster/caches ignite)

  (def test-cache
    (cache/get-or-create ignite
                         (org.apache.ignite.configuration.CacheConfiguration. "test")))

  (doseq [k (range 0 1000)]
    (cache/put test-cache (str "key_" k) (str "value_" k)))

  (map #(cache/get test-cache (str "key_" %))
       (range 0 10))

  (cache/size test-cache)

  (cache/size test-cache [:primary :backup])

  (cache/size test-cache [:backup])

  (cache/remove test-cache "key_0")

  (cache/destroy! test-cache)

  (print/topology ignite [:id :consistent-id])

  (print/baseline ignite [:id :consistent-id :online?])

  (print/caches ignite)

)
