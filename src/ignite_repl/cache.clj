(ns ignite_repl.cache
  (:gen-class)
  (:refer-clojure :exclude [get remove contains?])
  (:require [ignite_repl.ignite :refer [ignite]])
  (:import [org.apache.ignite IgniteCache]
           [org.apache.ignite.cache CachePeekMode]))

(defn get-or-create [cfg]
  (-> ignite
      (.getOrCreateCache cfg)))

(defn create [cfg]
  (-> ignite
      (.createCache cfg)))

(defn cache [name]
  (-> ignite
      (.cache name)))

(defn size
  ([^IgniteCache c]
   (size c nil))
  ([^IgniteCache c modes]
   (-> c
       (.size (->> modes
                   (map #(-> %
                             name
                             .toUpperCase
                             CachePeekMode/valueOf))
                   vec
                   (into-array CachePeekMode))))))

(defn get [^IgniteCache c k]
  (.get c k))

(defn put [^IgniteCache c k v]
  (.put c k v))

(defn remove [^IgniteCache c k]
  (.remove c k))

(defn contains? [^IgniteCache c k]
  (.contains c k))

(defn destroy! [^IgniteCache c]
  (.destroy c))

(defn lost-partitions [^IgniteCache c]
  (-> (.lostPartitions c)
      vec))

(defn reset-lost-partitions! [cache-names]
  (-> ignite
      (.resetLostPartitions cache-names)))
