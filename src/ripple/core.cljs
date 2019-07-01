
(ns ripple.core (:require-macros [ripple.core]))

(defn atom? [x] (= Atom (type x)))

(defn observe-vars [sources f]
  (assert (every? atom? sources) "All sources should be atoms.")
  (let [*prev-vars (atom (map deref sources))
        *result (atom (apply f @*prev-vars))
        caller (fn []
                 (let [current-vars (map deref sources)]
                   (when (not= current-vars @*prev-vars)
                     (reset! *result (apply f (map deref sources)))
                     (reset! *prev-vars current-vars))))]
    (doseq [source sources] (if (atom? source) (add-watch source :observe caller)))
    *result))

(defn observe-vars-effect [x0 sources effect detect]
  (assert (every? atom? sources) "sources should be atoms!")
  (assert (fn? effect) "effect should be function!")
  (assert (fn? detect) "detect should be function!")
  (let [*result (atom x0)
        *prev-vars (atom (doall (map deref sources)))
        modify! (fn [x] (reset! *result x))
        caller (fn []
                 (let [current-vars (map deref sources)]
                   (when (and (not= current-vars @*prev-vars) (apply detect current-vars))
                     (apply effect (cons modify! current-vars))
                     (reset! *prev-vars current-vars))))]
    (doseq [source sources] (add-watch source :observe caller))
    *result))
