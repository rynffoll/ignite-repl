(ns ignite_repl.ignite
  (:gen-class)
  (:import [org.apache.ignite Ignite Ignition]
           org.apache.ignite.cluster.ClusterNode
           org.apache.ignite.lang.IgniteCallable))

(def ignite (atom nil))

(defn ignite-start [c]
  (Ignition/start c))

(defn ignite-stop []
  (Ignition/stop true))

(defn- ClusterNode->map [^ClusterNode n]
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

(defn cluster-nodes [^Ignite ig]
  (->> ig
       (.cluster)
       (.nodes)
       (map ClusterNode->map)))

(defn- enum->keyword [e]
  (-> e
      .name
      .toLowerCase
      keyword))

(defn ignite-state?
  ([] (ignite-state? nil))
  ([n] (-> (Ignition/state n)
              enum->keyword)))

(defn cluster-activated? [^Ignite i]
  (.active i))

(defn cluster-activate! [^Ignite i]
  (.active i true))

(defn cluster-deactivate! [^Ignite i]
  (.active i false))

(comment
  (set! *warn-on-reflection* true)

  @ignite

  (swap! ignite (fn [_] (ignite-start "resources/client_2.xml")))

  (ignite-stop)

  (ignite-state?)

  (cluster-activated? @ignite)

  (do (cluster-activate! @ignite)
      (cluster-activated? @ignite))

  (do (cluster-deactivate! @ignite)
      (cluster-activated? @ignite))

  (->> (cluster-nodes @ignite)
       (map :consistent-id))

  (-> @ignite
      (.compute (-> @ignite
                    .cluster
                    .forServers))
      (.broadcast (reify IgniteCallable
                    (call [_]
                      (println "::: test :::"))))
      )

  (.call (reify IgniteCallable
           (call [_]
             (println "::: test :::"))))
  )
