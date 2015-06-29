(ns clj-free.core
  (:require [clj-scott-adts.core :refer [defadt match-adt]]
            [clojure.algo.generic.functor :refer [fmap]]
            [clojure.algo.monads :refer [defmonad]]))

(defadt Either   ; * -> * -> *
  (left a)       ; a -> Either a b
  (right b))     ; b -> Either a b

(defmethod print-method Either [v ^java.io.Writer w]
  (.write w (either
             (partial format "(left %s)")
             (partial format "(right %s)")
             v)))

(defadt Free     ; * -> * -> *
  (done a)       ; a -> Free f a
  (more f)       ; f (Free f a) -> Free f a
  (bind* t f))   ; Free f a -> (a -> Free f b) -> Free f b

(defmethod print-method Free [v ^java.io.Writer w]
  (.write w (free
             (partial format "(done %s)")
             (partial format "(more %s)")
             (partial format "(bind* %s %s)")
             v)))

;;; Free f a -> (a -> Free f b) -> Free f b
(defn bind
  [t f]
  (match-adt t
             (bind* a g) (bind* a (fn [x] (bind (g x) f)))
             (bind* t f)))

(defn lift-f
  [command]
  (more (fmap done command)))

;;; Free f a -> Either (f (Free f a)) a
(defn resume
  [m]
  (match-adt m
             (done a) (right a)
             (more k) (left k)
             (bind* a f) (match-adt a
                                    (done a) (recur (f a))
                                    (more k) (left (fmap #(bind % f) k))
                                    (bind* b g) (recur (bind b (fn [x] (bind (g x) f)))))))

(defn run-free
  [phi t]
  (match-adt (resume t)
             (left x) (recur phi (phi x))
             (right x) x))

(defmonad free-m
  [m-result done
   m-bind   bind])

(defmethod fmap Free
  [f x]
  (bind x (comp done f)))
