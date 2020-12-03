(ns aoc.day02
  (:gen-class)
  (:require [aoc.core :refer [get-lines split-words]]))
  
(defn create-record
  [record]
  (let [[rules password] (split-words record #": ") [numbers letter] (split-words rules #" ") [min max] (split-words numbers #"-")]
    { :min (Integer. min) :max (Integer. max) :letter (first letter) :password password }))
    
(def input (vec (map #(create-record %) (get-lines "resources/02.txt"))))

(defn validate-01
  [record]
  (let [list (frequencies (record :password))]
    (>= (record :max) (or (list (record :letter)) 0) (record :min))))

(defn pin
  [password pos letter]
  (if (< (count password) pos)
    false
    (= (nth password pos) letter)))

(defn validate-02
  [record]
  (let [letter (record :letter) password (record :password)]
;;    (println letter password (record :min) (record :max))
    (= (pin password (- (record :min) 1) letter) (not (pin password (- (record :max) 1) letter)))))
    
(defn validate-count
  ([records]
    (validate-count records validate-01))
  ([records validator]
  (let [list (frequencies (map #(validator %) records))]
    (list true))))

(defn solve-02-1
  []
  (let [sum (validate-count input)]
    sum))

(defn solve-02-2
  []
  (let [sum (validate-count input validate-02)]
    sum))
