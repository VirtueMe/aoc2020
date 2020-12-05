(ns aoc.day01
  "This solves the first and second part of the puzzle for day 1.
  
   See <https://adventofcode.com/2020/day/1>
   
   Before you leave, the Elves in accounting just need you to fix your expense 
   report (your puzzle input); apparently, something isn't quite adding up."
  (:gen-class)
  (:require [aoc.core :refer [get-lines]]))
  
(def input 
  "Fetch the numbers from the input given. The input might not be the same as you have, 
   but the solution to solve it will be the same."
  (vec (map #(Integer. %) (get-lines "resources/01.txt"))))

(def goal 2020)

(defn find-numbers
  "loop through the list of numbers. Take the first number, extract it from goal, 
  and then try to find the exact match for this number in the rest of the list.
  Return the two numbers that match."
  [input sum]
  (loop [list input]
    (if (= (count list) 1)
      nil
      (let [value (- sum (first list)) index (.indexOf (rest list) value)]
          (if (> index -1)
            [(first list) (nth (rest list) index)]
            (recur (rest list)))))))

(defn find-numbers-advanced
  "Based on find-numbers. But here we use the first number substract it from goal, 
  and then we try to find the two numbers that is the new goal."
  [input sum]
  (loop [list input]
    (if (< (count list) 3)
      nil
      (let [value (- sum (first list)) tuple (find-numbers (rest list ) value)]
        (if (not (nil? tuple))
          (flatten [(first list) tuple])
          (recur (rest list)))))))
            
(defn sum-numbers
  "Calculate the sum of the numbers in the list."
  [input]
  (apply * input))
  
(defn part-1
  "Find the two entries that sum to 2020; what do you get if you multiply them together?"
  ([]
    (part-1 input))
  ([input]
    (sum-numbers (find-numbers input goal))))

(defn part-2
  "what is the product of the three entries that sum to 2020?"
  ([]
    (part-2 input))
  ([input]
    (sum-numbers (find-numbers-advanced input goal))))

(defn solve
  ([]
    (solve part-1))
  ([part]
    (solve part input))
  ([part input]
    (part input)))
