(ns aoc.day05
  "This solves the first and second part of the puzzle for day 5.
  
   See <https://adventofcode.com/2020/day/5>
  
   You board your plane only to discover a new problem: you dropped your
   boarding pass! You aren't sure which seat is yours, and all of the flight
   attendants are busy with the flood of people that suddenly made it through
   passport control.

   You write a quick program to use your phone's camera to scan all of the
   nearby boarding passes (your puzzle input); perhaps you can find your seat
   through process of elimination.

   Instead of zones or groups, this airline uses binary space partitioning to
   seat people. A seat might be specified like FBFBBFFRLR, where F means
   \"front\", B means \"back\", L means \"left\", and R means \"right\"."
  (:gen-class)
  (:require [aoc.core :refer [get-lines]]))

(def input (vec (map #(str %) (get-lines "resources/05.txt"))))

(def rows
  "128 rows on the plane (numbered 0 through 127)"
  [0 127])

(def columns
  "8 columns of seats on the plane (numbered 0 through 7)"
  [0 7])

(defn pick-exact
  [low high]
  (if (< high low) (+ low high) high))

(defn F
  "F means \"front\""
  [low high]
  (let [middle (- (/ (+ (- high low) 1) 2) 1)]
    [low (pick-exact low middle)]))

(defn B
  "B means \"back\""
  [low high]
  (let [middle (/ (+ (- high low) 1) 2)]
    [(pick-exact low middle) high]))

(defn R
  "R means \"right\""
  [low high]
    (B low high))

(defn L
  "L means \"left\""
  [low high]
    (F low high))

(defn exec-calc
  "Translate letter to function and run it"
  [input pos]
  ((load-string (str "aoc.day05/" input)) (first pos) (second pos)))

(defn calc-item
  ([input]
    (calc-item input rows))
  ([input pos]
    (loop [items input pos pos]
      (if (empty? items)
        (first pos)
        (recur (rest items) (exec-calc (first items) pos))))))

(defn calc-seat-id
  "Every seat also has a unique seat ID: multiply the row by 8, then add the column
  Splits input into rowinput and column input and calculates the unique seat ID"
  [input]
    (let [rowinput (take 7 input) columninput (drop 7 input)]
      (+ (* (calc-item rowinput rows) 8) (calc-item columninput columns))))

(defn find-free-seat
  "finds missing id in numbers. Must be sorted"
  [input]
    (loop [seats (rest input) last (first input)]
      (if (= (- (first seats) last) 2)
        (+ last 1)
        (recur (rest seats) (first seats)))))

(defn part-1
  "What is the highest seat ID on a boarding pass?"
  ([]
    (part-1 input))
  ([input]
    (apply max (map calc-seat-id input))))
    

(defn part-2
  "What is the ID of your seat"
  ([]
    (part-2 input))
  ([input]
    (find-free-seat (sort (map calc-seat-id input)))))

(defn solve
  ([]
    (solve part-1))
  ([part]
    (solve part input))
  ([part input]
    (part input)))
