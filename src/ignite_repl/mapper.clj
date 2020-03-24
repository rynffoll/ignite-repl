(ns ignite_repl.mapper
  (:gen-class)
  (:import org.apache.ignite.internal.cluster.DetachedClusterNode
           org.apache.ignite.spi.discovery.tcp.internal.TcpDiscoveryNode
           org.apache.ignite.spi.discovery.zk.internal.ZookeeperClusterNode
           org.apache.ignite.configuration.CacheConfiguration
           org.apache.ignite.internal.binary.BinaryTypeImpl))

(defmulti ->map type)

(defmethod ->map TcpDiscoveryNode
  [^TcpDiscoveryNode n]
  {:id            (.id n)
   :consistent-id (.consistentId n)
   :order         (.order n)

   :local?        (.isLocal n)
   :daemon?       (.isDaemon n)
   :client?       (.isClient n)

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

   :local?        (.isLocal n)
   :daemon?       (.isDaemon n)
   :client?       (.isClient n)

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

   :local?        (.isLocal n)
   :daemon?       (.isDaemon n)
   :client?       (.isClient n)

   :attributes    (.attributes n)
   })

(defmethod ->map CacheConfiguration
  [^CacheConfiguration c]
  {:name                       (.getName c)

   :cache-mode                 (.getCacheMode c)
   :atomicity-mode             (.getAtomicityMode c)

   :write-synchronization-mode (.getWriteSynchronizationMode c)
   :backups                    (.getBackups c)
   :read-from-backup?          (.isReadFromBackup c)

   :data-region-name           (.getDataRegionName c)

   :partition-loss-policy      (.getPartitionLossPolicy c)

   :affinity                   (.getAffinity c)
   })

(defmethod ->map BinaryTypeImpl
  [^BinaryTypeImpl b]
  {:type-id     (.typeId b)
   :type-name   (.typeName b)

   :field-names (.fieldNames b)

   :enum?       (.isEnum b)
   :enum-values (.enumValues b)
   })
