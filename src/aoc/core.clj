(ns aoc.core
  (:require [clojure.java.io :refer [reader]]
            [clojure.string :refer [split join]]))

(defn join-words
  ([input]
    (join-words input #" "))
  ([input delimiter]
    (join delimiter input)))
    
(defn split-letters
  ([str]
    (split-letters str #""))
  ([str letter]
    (split str letter)))

(defn split-words
  ([str]
    (split-words str #" "))
  ([str letter]
    (split-letters str letter)))

(defn split-cs-words
  [str]
  (split-words str #", "))

(defn get-lines
  "read file into collection"
  [fname]
  (with-open [r (reader fname)]
    (doall (line-seq r))))
