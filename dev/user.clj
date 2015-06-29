(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clj-scott-adts.core :refer [defadt match-adt]]
            [clj-free.core :refer [done more bind run-free lift-f free-m]]
            [clojure.algo.generic.functor :refer [fmap]]
            [clojure.algo.monads :refer [domonad]]))

(defadt ExprF
  (quitF)
  (printF str next)
  (bellF next)
  (readF nextf))

(defmethod fmap ExprF
  [f expr]
  (match-adt expr
             (quitF) quitF
             (printF str next) (printF str (f next))
             (bellF next) (bellF (f next))
             (readF nextf) (readF (comp f nextf))))

(def quit (lift-f quitF))

(defn print [str] (lift-f (printF str nil)))

(def bell (lift-f (bellF nil)))

(def read (lift-f (readF identity)))

(defn alg
  [expr]
  (match-adt expr
             (quitF) (done nil)
             (printF str next) (do (println str) next)
             (bellF next) (do (println "BELL!") next)
             (readF nextf) (nextf (read-line))))

(def test
  (domonad free-m
           [inp read
            _ (print inp)
            _ bell]
           quit))

