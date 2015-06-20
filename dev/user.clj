(ns user
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clj-mogensen-scott.core :refer [defadt match-adt]]
            [clj-monad-trampoline.core :refer [done more bind run-tramp]]))
