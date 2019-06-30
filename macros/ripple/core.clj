
(ns ripple.core
 (:require [clojure.string :as string]))

(defn replace-tree [exprs dict]
  (cond
    (list? exprs) (map (fn [x] (replace-tree x dict)) exprs)
    (symbol? exprs) (if (contains? dict exprs) (get dict exprs) exprs)
    :else (throw (str "Invalid code:" exprs))))

(defn format-name [x]
 (str (string/replace (name x) #"\*" "") "_"))

(defmacro defcomputed [fname sources & exprs]
 (assert (symbol? fname) "fname invalid")
 (assert (and (list? sources) (every? symbol? sources)) "sources invalid")
 (assert (not (empty? exprs)) "exprs invalid")
 (let [params (map (fn [x] (gensym (format-name x))) sources)
       dict (zipmap sources params)
       new-exprs (replace-tree exprs dict)]
   `(def ~fname (observe-vars [~@sources] (fn [~@params] ~@new-exprs)))))
