
Ripple Data
----

### Usage

#### `defcomputed`

```clojure
(:require [ripple.core :refer [defcomputed]])

(def *a (atom 1))

(def *b (atom 2))

(defcomputed *c [*a *b] (+ %a %b))

(println @*c) ; => 3

(reset! *a 2)

(println @*c) ; => 4
```

Variable names will be transformed to `%x` by removing `^\*`, for

```clojure
(defcomputed *c [*a *b] (+ %a %b))
```

it will be expended to:

```clojure
(def *c (ripple.core/observe-vars [*a *b]
            (clojure.core/fn [%a %b] (+ %a %b))))
```

#### effect

```clojure
(def *a1 (atom 1))

(def *a2 (atom 2))

(def *a3
  (observe-vars-effect nil [*a1 *a2]
   (fn [got a1 a2]
     (js/setTimeout (fn [] (got (+ a1 a2))) 2000))
   (fn [a1 a1] true)))

(add-watch *a3 :log (fn [] (println "change in a3" @*a3)))
(println @*a3)
(reset! *a1 3)
(println @*a3)
```

prints:

```text
nil
nil
change in a3 5
```

### License

MIT
