(ns aoc.day01
  (:gen-class)
  (:require [aoc.core :refer [get-lines]]))
  
(def input01 (vec (map #(Integer. %) (get-lines "resources/01.txt"))))

(def goal 2020)

(defn find-numbers
  [input sum]
  (loop [list input]
    (if (= (count list) 1)
      nil
      (let [value (- sum (first list)) index (.indexOf (rest list) value)]
          (if (> index -1)
            [(first list) (nth (rest list) index)]
            (recur (rest list)))))))

(defn find-numbers-advanced
  [input sum]
  (loop [list input]
    (if (< (count list) 3)
      nil
      (let [value (- sum (first list)) tuple (find-numbers (rest list ) value)]
        (if (not (nil? tuple))
          (concat [(first list)] tuple)
          (recur (rest list)))))))
            
(defn sum-numbers
  [input]
  (apply * input))
  
(defn solve-01
  [input]
  (let [sum (sum-numbers (find-numbers input goal))]
    sum))
  
(defn solve-01-1
  []
  (let [sum (sum-numbers (find-numbers input01 goal))]
    sum))
    
(defn solve-01-2
  []
  (let [sum (sum-numbers (find-numbers-advanced input01 goal))]
    sum))