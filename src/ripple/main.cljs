
(ns ripple.main (:require [ripple.core :refer [defcomputed observe-vars-effect]]))

(def *a (atom 1))

(def *a1 (atom 1))

(def *a2 (atom 2))

(def *a3
  (observe-vars-effect
   nil
   [*a1 *a2]
   (fn [got a1 a2] (js/setTimeout (fn [] (got (+ a1 a2))) 2000))
   (fn [a1 a1] true)))

(def *b (atom 2))

(defcomputed *c (*a *b) (+ %a %b))

(defn do-computed! []
  (println "c0" @*c)
  (comment add-watch *c :start (fn [] (println "c" @*c)))
  (reset! *a 2)
  (println "c0" @*c)
  (reset! *a 2)
  (println "c0" @*c)
  (comment (macroexpand '(defcomputed *c (*a *b) (+ %a %b)))))

(defn do-effect! []
  (add-watch *a3 :log (fn [] (println "change in a3" @*a3)))
  (println @*a3)
  (reset! *a1 3)
  (println @*a3))

(defn main! [] (println "started.") (comment do-computed!) (do-effect!))

(defn reload! [] (comment .clear js/console) (println "reloaded.") (js/process.exit 1))
