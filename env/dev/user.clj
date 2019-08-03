(ns user
  (:require [integrant.repl :refer [clear go halt prep init reset reset-all]]))

;; (integrant.repl/set-prep! (fn [] sys/config))

(comment

  ;; http://insideclojure.org/2018/05/04/add-lib/
  (require '[clojure.tools.deps.alpha.repl :refer [add-lib]])

  (add-lib 'integrant/repl {:mvn/version "0.3.1"})

  (require '[integrant.repl :refer [clear go halt prep init reset reset-all]])

  (integrant.repl/set-prep! (fn [] sys/config))

  (prep)

  (init)

  integrant.repl.state/system

  (go)

  (halt)

  (reset))
