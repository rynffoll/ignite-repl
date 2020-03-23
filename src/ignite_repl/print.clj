(ns ignite_repl.print
  (:gen-class)
  (:require [ignite_repl.cluster :as cluster]
            [ignite_repl.cache :as cache]
            [ignite_repl.binary :as binary]
            [ignite_repl.mapper :as m]
            [clojure.pprint :as pp])
  (:import [org.apache.ignite Ignite]
           [org.apache.ignite.configuration CacheConfiguration]))

(defn topology [keys]
  (->> (cluster/topology)
       (map #(select-keys % keys))
       pp/print-table))

(defn baseline [keys]
  (let [online (set (map :consistent-id (cluster/topology)))]
    (->> (cluster/baseline)
         (map #(merge % {:online? (contains? online (:consistent-id %))}))
         (map #(select-keys % keys))
         pp/print-table)))

(defn caches []
  (->> (cluster/caches)
       (map #(cache/cache %))
       (map #(.getConfiguration % CacheConfiguration))
       (map m/->map)
       pp/print-table))

(defn binary-types []
  (-> (binary/types)
      pp/print-table))
