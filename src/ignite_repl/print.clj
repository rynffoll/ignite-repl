(ns ignite_repl.print
  (:gen-class)
  (:require [ignite_repl.cluster :as cluster]
            [ignite_repl.cache :as cache]
            [ignite_repl.binary :as binary]
            [ignite_repl.mapper :as m]
            [clojure.pprint :as pp])
  (:import [org.apache.ignite.configuration CacheConfiguration]))

(defn topology [keys]
  (->> (cluster/topology)
       (map #(select-keys % keys))
       pp/print-table))

(defn baseline [keys]
  (->> (cluster/baseline)
       (map #(select-keys % keys))
       pp/print-table))

(defn- cache-configs []
  (->> (cluster/caches)
       (map #(cache/cache %))
       (map #(.getConfiguration % CacheConfiguration))
       (map m/->map)))

(defn caches [keys]
  (->> (cache-configs)
       (map #(select-keys % keys))
       pp/print-table))

(defn binary-types [keys]
  (->> (binary/types)
       (map #(select-keys % keys))
       pp/print-table))
