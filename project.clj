(defproject clj-free "0.1.0-SNAPSHOT"
  :description "Free monads"
  :url "https://github.com/tcsavage/clj-free"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/algo.generic "0.1.2"]
                 [org.clojure/algo.monads "0.1.5"]
                 [clj-mogensen-scott "0.1.0-SNAPSHOT"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.10"]]
                   :source-paths ["dev"]}})
