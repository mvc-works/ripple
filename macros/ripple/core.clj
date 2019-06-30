
(ns ripple.core
 (:require [clojure.string :as string]))

(defn replace-tree [exprs dict]
  "replace generated parameter in body"
  (cond
    (list? exprs) (map (fn [x] (replace-tree x dict)) exprs)
    (symbol? exprs) (if (contains? dict exprs) (get dict exprs) exprs)
    :else (throw (str "Invalid code:" exprs))))

(defn format-name [x]
 (str "%" (string/replace (name x) #"^\*" "")))

(defmacro defcomputed [fname sources & exprs]
 "parameter *x will be rewritten to %x"
 (assert (symbol? fname) "fname invalid")
 (assert (and (list? sources) (every? symbol? sources)) "sources invalid")
 (assert (not (empty? exprs)) "exprs invalid")
 (let [params (map (fn [x] (symbol (format-name x))) sources)]
   `(def ~fname (observe-vars [~@sources] (fn [~@params] ~@exprs)))))
