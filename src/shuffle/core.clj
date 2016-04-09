(ns shuffle.core
  (:require [shuffle.random :as rand]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn -main
  "Run"
  [& args]
  (print (take 10 (rand/random-numbers))))
