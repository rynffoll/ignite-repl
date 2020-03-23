(ns ignite_repl.binary
  (:gen-class)
  (:refer-clojure :exclude [type])
  (:require [ignite_repl.mapper :as m]
            [ignite_repl.ignite :refer [ignite]]))

(defn binary []
  (-> ignite
      .binary))

(defn types []
  (->> (binary)
       .types
       (map #(m/->map %))))

(defn type [type]
  (-> (binary)
      (.type type)))

(defn type-id [type]
  (-> (binary)
      (.typeId type)))
