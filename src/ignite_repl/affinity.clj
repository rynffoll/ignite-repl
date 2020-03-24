(ns ignite_repl.affinity
  (:gen-class)
  (:require [ignite_repl.ignite :refer [ignite]]
            [ignite_repl.mapper :as m]))

(defn affinity [cache-name]
  (-> ignite
      (.affinity cache-name)))

(defn affinity-key [cache-name key]
  (-> (affinity cache-name)
      (.affinityKey key)))

(defn all-partitions [cache-name n]
  (-> (affinity cache-name)
      (.allPartitions n)))

(defn backup-partitions [cache-name n]
  (-> (affinity cache-name)
      (.backupPartitions n)))

(defn is-backup [cache-name n key]
  (-> (affinity cache-name)
      (.isBackup n key)))

(defn is-primary [cache-name n key]
  (-> (affinity cache-name)
      (.isPrimary n key)))

(defn is-primary-or-backup [cache-name n key]
  (-> (affinity cache-name)
      (.isPrimaryOrBackup n key)))

(defn map-keys-to-nodes [cache-name keys]
  (-> (affinity cache-name)
      (.mapKeysToNodes keys)))

(defn map-key-to-node [cache-name key]
  (-> (affinity cache-name)
      (.mapKeyToNode key)
      (m/->map)))

(defn map-key-to-primary-and-backups [cache-name key]
  (-> (affinity cache-name)
      (.mapKeyToPrimaryAndBackups key)
      (->> (map m/->map))))

(defn map-partitions-to-nodes [cache-name parts]
  (-> (affinity cache-name)
      (.mapPartitionsToNodes (map int parts))
      (->> (into {})
           (reduce-kv (fn [m k v] (assoc m k (m/->map v))) {}))))

(defn map-partition-to-node [cache-name part]
  (-> (affinity cache-name)
      (.mapPartitionToNode (int part))
      (m/->map)))

(defn map-partition-to-primary-and-backups [cache-name part]
  (-> (affinity cache-name)
      (.mapPartitionToPrimaryAndBackups (int part))
      (->> (map m/->map))))

(defn partition [cache-name key]
  (-> (affinity cache-name)
      (.partition key)))

(defn partitions [cache-name]
  (-> (affinity cache-name)
      (.partitions)))

(defn primary-partitions [cache-name n]
  (-> (affinity cache-name)
      (.primaryPartitions n)))
