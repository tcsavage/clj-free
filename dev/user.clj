(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clj-mogensen-scott.core :refer [defadt match-adt]]
            [clj-monad-trampoline.core :refer [done more bind run-tramp]]))

(defadt ListF    ; * -> * -> *
  (cons* a la)   ; a -> r -> MyList a r
  (empty*))      ; MyList a r

(defn map-listf
  [f xs]
  (match-adt xs
    (cons* a as) (bind (more #(f as)) (comp done (partial cons* a)))
    (done xs)))

(defn length-alg
  [l]
  (match-adt l
    (cons* a as) (inc as)
    0))

(defn cata*
  [alg l]
  (bind (map-listf (partial cata* alg) l) alg))

(defn cata
  [alg l]
  (run-tramp (cata* (comp done alg) l)))