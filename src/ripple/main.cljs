
(ns ripple.main (:require [ripple.core :refer [defcomputed]]))

(def *a (atom 1))

(def *b (atom 2))

(defcomputed *c (*a *b) (+ %a %b))

(defn *d [] )

(defn main! []
  (println "started.")
  (println "c0" @*c)
  (comment add-watch *c :start (fn [] (println "c" @*c)))
  (reset! *a 2)
  (println "c0" @*c)
  (comment (macroexpand '(defcomputed *c (*a *b) (+ %a %b)))))

(defn reload! [] (comment .clear js/console) (println "reloaded."))
