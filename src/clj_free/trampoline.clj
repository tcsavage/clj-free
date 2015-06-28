(ns clj-free.trampoline
  "A trampoline is simply the free monad over nullary functions."
  (:require [clj-free.core :refer [run-free]]))

(def run-trampoline (partial run-free (memfn invoke)))
