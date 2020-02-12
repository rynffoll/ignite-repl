(ns ignite_repl.cache
  (:gen-class)
  (:refer-clojure :exclude [get remove contains?])
  (:import [org.apache.ignite Ignite IgniteCache]
           [org.apache.ignite.cache CachePeekMode]))

(defn get-or-create [^Ignite ig cfg]
  (.getOrCreateCache ig cfg))

(defn create [^Ignite ig cfg]
  (.createCache ig cfg))

(defn cache [^Ignite ig name]
  (.cache ig name))

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
