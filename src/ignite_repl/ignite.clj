(ns ignite_repl.ignite
  (:gen-class)
  (:require [mount.core :refer [defstate]]
            [ignite_repl.config :refer [config]])
  (:import [org.apache.ignite Ignition]))

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
