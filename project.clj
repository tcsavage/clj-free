(defproject clj-monad-trampoline "0.1.0-SNAPSHOT"
  :description "Trampoline monad"
  :url "https://github.com/tcsavage/clj-monad-trampoline"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-mogensen-scott "0.1.0-SNAPSHOT"]]
  :profiles {:dev {:dependencies [[org.clojure/tools.namespace "0.2.10"]]
                   :source-paths ["dev"]}})
