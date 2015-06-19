(ns clj-monad-trampoline.core
  (:require [clj-mogensen-scott.core :refer [defadt match-adt]]))

(defadt Either   ; * -> * -> *
  (left a)       ; a -> Either a b
  (right b))     ; b -> Either a b

(defadt Tramp    ; * -> *
  (done a)       ; a -> Tramp a
  (more c)       ; (() -> Tramp a) -> Tramp a
  (bind* t f))   ; Tramp a -> (a -> Tramp b) -> Tramp b

;;; Tramp a -> (a -> Tramp b) -> Tramp b
(defn bind
  [t f]
  (match-adt t
             (bind* a g) (bind* a (fn [x] (bind (g x) f)))
             (bind* t f)))

;;; Tramp a -> Either (() -> Tramp a) a
(defn resume
  [t]
  (match-adt t
    (done v) (right v)
    (more k) (left k)
    (bind* a f) (match-adt a
                     (done v) (recur (f v))
                     (more k) (left #(bind (k) f))
                     (bind* b g) (recur (bind b (fn [x] (bind (g x) f)))))))

;;; Tramp a -> a
(defn run-tramp
  [t]
    (match-adt (resume t)
               (left x) (recur (x))
               (right x) x))