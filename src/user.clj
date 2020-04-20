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

  (def mem-cache
    (cache/get-or-create
     (-> (org.apache.ignite.configuration.CacheConfiguration.)
         (.setName "mem")
         (.setBackups 1))))

  (doseq [k (range 0 1000)]
    (cache/put mem-cache (str "key_" k) (str "value_" k)))

  (map #(cache/get mem-cache (str "key_" %))
       (range 0 10))

  (cache/get-all mem-cache (map #(str "key_" %) (range 0 10)))

  (cache/size mem-cache)

  (cache/size mem-cache [:primary :backup])

  (cache/size mem-cache [:backup])

  (cache/remove mem-cache "key_0")

  (cache/destroy! mem-cache)

  (->> (affinity/map-partitions-to-nodes "mem" [0 1])
       (reduce-kv (fn [m k v]
                    (assoc m k (select-keys v [:consistent-id])))
                  {}))

  (->> (affinity/map-partition-to-primary-and-backups "mem" 0)
       (map #(select-keys % [:consistent-id])))

  (->> (affinity/map-key-to-primary-and-backups "mem" "key_0")
       (map #(select-keys % [:consistent-id])))

  (affinity/partitions "mem")

  (affinity/partition "mem" "key_0")

  (->> (affinity/map-key-to-primary-and-backups "mem" "key_0")
       (map #(select-keys % [:consistent-id])))

  (def disk-cache
    (cache/get-or-create
     (-> (org.apache.ignite.configuration.CacheConfiguration.)
         (.setName "disk")
         (.setDataRegionName "Persistence Region")
         (.setBackups 1))))

  (cache/wal? "disk")

  (cache/enable-wal! "disk")

  (cache/disable-wal! "disk")

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
                 ;; :read-from-backup?

                 :data-region-name

                 :partition-loss-policy
                 ])

  (print/binary-types [:type-id
                       :type-name

                       ;; :field-names

                       ;; :enum?
                       ;; :enum-values
                       ])

  (defn template [cn dr]
    (cache/get-or-create
     (-> (org.apache.ignite.configuration.CacheConfiguration.)
         (.setName cn)
         (.setBackups 1)
         (.setDataRegionName dr)
         (.setWriteSynchronizationMode org.apache.ignite.cache.CacheWriteSynchronizationMode/FULL_SYNC)
         (.setPartitionLossPolicy org.apache.ignite.cache.PartitionLossPolicy/READ_ONLY_SAFE)
         (.setAffinity
          (-> (org.apache.ignite.cache.affinity.rendezvous.RendezvousAffinityFunction.)
              (.setAffinityBackupFilter
               (org.apache.ignite.cache.affinity.rendezvous.ClusterNodeAttributeAffinityBackupFilter. (into-array java.lang.String ["dc"])))))
         )))

  (def p-cache (template "p-cache" "Persistence Region"))

  (def m-cache (template "m-cache" "Memory Region"))

  (doseq [k (range 0 1000)]
    (cache/put p-cache (str "pk_" k) (str "pv_" k))
    (cache/put m-cache (str "mk_" k) (str "mv_" k)))

  (doseq [k (range 0 1000)]
    (println (cache/get p-cache (str "pk_" k))
             (cache/get m-cache (str "mk_" k))))

  (cache/size p-cache)

  (cache/size m-cache)

  (cache/destroy! p-cache)

  (cache/destroy! m-cache)

  (cache/lost-partitions p-cache)

  (cache/lost-partitions m-cache)

)
