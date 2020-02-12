(ns ignite_repl.config
  (:gen-class)
  (:require [mount.core :refer [defstate]]
            [aero.core :refer [read-config]]
            [clojure.java.io :as io]))

(defstate config
  :start (read-config (io/resource "config.edn")))
