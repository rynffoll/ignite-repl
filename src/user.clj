(ns user
  (:require [mount.core :as mount]
            [ignite_repl.config :refer [config]]
            [ignite_repl.ignite :refer [ignite] :as ig]
            [ignite_repl.cluster :as cluster]
            [ignite_repl.cache :as cache]
            [ignite_repl.affinity :as affinity]
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

  (cluster/activated?)

  (cluster/activate!)

  (cluster/deactivate!)

  (cluster/topology-version)

  (cluster/topology (cluster/topology-version))

  (cluster/topology)

  (cluster/baseline)

  (cluster/caches)

  (def test-cache
    (cache/get-or-create
     (-> (org.apache.ignite.configuration.CacheConfiguration.)
         (.setName "test")
         (.setBackups 1))))

  (doseq [k (range 0 1000)]
    (cache/put test-cache (str "key_" k) (str "value_" k)))

  (map #(cache/get test-cache (str "key_" %))
       (range 0 10))

  (cache/size test-cache)

  (cache/size test-cache [:primary :backup])

  (cache/size test-cache [:backup])

  (cache/remove test-cache "key_0")

  (cache/destroy! test-cache)

  (->> (affinity/map-partitions-to-nodes "test" [0 1])
       (reduce-kv (fn [m k v]
                    (assoc m k (select-keys v [:consistent-id])))
                  {}))

  (->> (affinity/map-partition-to-primary-and-backups "test" 0)
       (map #(select-keys % [:consistent-id])))

  (->> (affinity/map-key-to-primary-and-backups "test" "key_0")
       (map #(select-keys % [:consistent-id])))

  (affinity/partitions "test")

  (affinity/partition "test" "key_0")

  (->> (affinity/map-key-to-primary-and-backups "test" "key_0")
       (map #(select-keys % [:consistent-id])))

  (print/topology [:id
                   :consistent-id
                   :order

                   :client?])

  (print/baseline [:id
                   :consistent-id

                   :online?])

  (print/caches [:name

                 :cache-mode
                 :atomicity-mode

                 :write-synchronization-mode
                 :backups
                 :read-from-backup?

                 :data-region-name

                 :partition-loss-policy
                 ])

  (print/binary-types [:type-id
                       :type-name

                       :field-names

                       :enum?
                       :enum-values
                       ])

)
