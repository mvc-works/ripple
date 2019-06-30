
Ripple Data
----

### Usage

```clojure
(:require [ripple.core :refer [defcomputed]])

(def *a (atom 1))

(def *b (atom 2))

(defcomputed *c [*a *b] (+ %a %b))

(println @*c)

(reset! *a 2)

(println @*c)
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

### License

MIT
