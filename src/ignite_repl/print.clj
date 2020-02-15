(ns ignite_repl.print
  (:gen-class)
  (:require [ignite_repl.cluster :as cluster]
            [clojure.pprint :as pp])
  (:import [org.apache.ignite Ignite]
           [org.apache.ignite.configuration CacheConfiguration]))

(defn topology [^Ignite ig keys]
  (->> ig
       cluster/topology
       (map #(select-keys % keys))
       pp/print-table))

(defn baseline [^Ignite ig keys]
  (let [online (set (map :consistent-id (cluster/topology ig)))]
    (->> ig
         cluster/baseline
         (map #(merge % {:online? (contains? online (:consistent-id %))}))
         (map #(select-keys % keys))
         pp/print-table)))

(defn caches [^Ignite ig]
  (->> ig
       cluster/caches
       (map #(.cache ig %))
       (map #(.getConfiguration % CacheConfiguration))
       (map cluster/->map)
       pp/print-table))
