
(ns ripple.core (:require-macros [ripple.core]))

(defn atom? [x] (= Atom (type x)))

(defn observe-vars [sources f]
  (assert (every? atom? sources) "All sources should be atoms.")
  (let [*result (atom (apply f (map deref sources)))
        caller (fn [] (reset! *result (apply f (map deref sources))))]
    (doseq [source sources] (if (atom? source) (add-watch source :observe caller)))
    *result))

(defn observe-vars-effect [x0 sources detect effect]
  (let [*result (atom x0), modify! (fn [x] (reset! *result x))]
    (doseq [source sources] )
    *result))
