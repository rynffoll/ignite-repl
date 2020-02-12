(ns ignite_repl.ignite
  (:gen-class)
  (:require [mount.core :refer [defstate]]
            [ignite_repl.config :refer [config]]
            [ignite_repl.cluster :as cluster]
            [ignite_repl.cache :as cache])
  (:import [org.apache.ignite Ignite Ignition]))

(defn ignite-start! [cfg]
  (Ignition/start cfg))

(defn ignite-stop! [cancel?]
  (Ignition/stop cancel?))

(defn- enum->keyword [e]
  (-> e
      .name
      .toLowerCase
      keyword))

(defn state?
  ([]     (state? nil))
  ([name] (-> (Ignition/state name)
              enum->keyword)))

(defstate ignite
  :start (let [config (:ignite/config config)]
           (ignite-start! config))
  :stop  (ignite-stop! true))

(comment


  )
