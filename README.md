
Ripple Data
----

### Usage

```clojure
(:require [ripple.core :refer [defcomputed]])

(def *a (atom 1))

(def *b (atom 2))

(defcomputed *c [*a *b] (+ *a *b))

(println @*c)

(reset! *a 2)

(println @*c)
```

### License

MIT
