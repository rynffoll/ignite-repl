(ns ignite_repl.cluster
  (:gen-class)
  (:require [ignite_repl.mapper :as m]
            [ignite_repl.ignite :refer [ignite]]))

(defn activated? []
  (-> ignite
      .active))

(defn activate! []
  (-> ignite
      (.active true)))

(defn deactivate! []
  (-> ignite
      (.active false)))

(defn cluster []
  (-> ignite
      .cluster))

(defn topology-version []
  "Gets a topology by version."
  (-> (cluster)
      .topologyVersion))

(defn topology
  ([ver]
   (-> (cluster)
       (.topology ver)
       vec
       (->> (map m/->map))))
  ([]
   (topology (topology-version))))

(defn baseline []
  (let [online (set (map :consistent-id (topology)))]
    (-> (cluster)
        .currentBaselineTopology
        (->> (map m/->map)
             (map #(assoc % :online? (contains? online (:consistent-id %))))))))

(defn caches []
  (-> ignite
      .cacheNames
      vec))

(defn enable-wal! [cache]
  (-> (cluster)
      .enableWal))

(defn disable-wal! [cache]
  (-> (cluster)
      .disableWal))
