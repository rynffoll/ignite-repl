(ns ignite_repl.cluster
  (:gen-class)
  (:import [org.apache.ignite Ignite Ignition]
           org.apache.ignite.internal.cluster.DetachedClusterNode
           org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode
           org.apache.ignite.spi.discovery.zk.internal.ZookeeperClusterNode
           org.apache.ignite.configuration.CacheConfiguration))

(defmulti ->map type)

(defmethod ->map TcpDiscoveryNode
  [^TcpDiscoveryNode n]
  {:id            (.id n)
   :consistent-id (.consistentId n)
   :order         (.order n)

   :local         (.isLocal n)
   :daemon        (.isDaemon n)
   :client        (.isClient n)

   :addresses     (.addresses n)
   :host-names    (.hostNames n)
   :attributes    (.attributes n)
   :metrics       (.metrics n)

   :version       (.version n)
   })

(defmethod ->map ZookeeperClusterNode
  [^ZookeeperClusterNode n]
  {:id            (.id n)
   :consistent-id (.consistentId n)
   :order         (.order n)

   :local         (.isLocal n)
   :daemon        (.isDaemon n)
   :client        (.isClient n)

   :addresses     (.addresses n)
   :host-names    (.hostNames n)
   :attributes    (.attributes n)
   :metrics       (.metrics n)

   :version       (.version n)
   })

(defmethod ->map DetachedClusterNode
  [^DetachedClusterNode n]
  {:id            (.id n)
   :consistent-id (.consistentId n)
   ;:order         (.order n)

   :local         (.isLocal n)
   :daemon        (.isDaemon n)
   :client        (.isClient n)

   ;:addresses     (.addresses n)
   ;:host-names    (.hostNames n)
   :attributes    (.attributes n)
   ;:metrics       (.metrics n)

   ;:version       (.version n)
   })

(defmethod ->map CacheConfiguration
  [^CacheConfiguration c]
  {:name (.getName c)
   :atomicity-mode (.getAtomicityMode c)
   :backups (.getBackups c)
   :partition-loss-policy (.getPartitionLossPolicy c)
   :write-synchronization-mode (.getWriteSynchronizationMode c)
   :data-region-name (.getDataRegionName c)
   })

(defn activated? [^Ignite ig]
  (.active ig))

(defn activate! [^Ignite ig]
  (.active ig true))

(defn deactivate! [^Ignite ig]
  (.active ig false))

(defn cluster [^Ignite ig]
  (.cluster ig))

(defn topology [^Ignite ig]
  (let [cluster (cluster ig)
        topology-version (.topologyVersion cluster)
        topology (-> cluster
                     (.topology topology-version)
                     vec)]
    (map ->map topology)))

(defn baseline [^Ignite ig]
  (-> ig
      cluster
      .currentBaselineTopology
      (->> (map ->map))))

(defn caches [^Ignite ig]
  (-> ig
      .cacheNames
      vec))
