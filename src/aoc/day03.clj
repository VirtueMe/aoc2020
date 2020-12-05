(ns aoc.day03
  "This solves the first and second part of the puzzle for day 3.
   See <https://adventofcode.com/2020/day/3>
   
   Due to the local geology, trees in this area only grow on exact integer coordinates
   in a grid. You make a map (your puzzle input) of the open squares (.) and trees (#) 
   you can see"
  (:gen-class)
  (:require [aoc.core :refer [get-lines]]))

(def input
  "Fetch the tree pattern from the input given. The input might not be the same as you have, 
   but the solution to solve it will be the same."
  (vec (map str (get-lines "resources/03.txt"))))

(def start
  "You start on the open square (.) in the top-left corner and need to reach the
   bottom (below the bottom-most row on your map)"
   [0 0])
   
(def default-step 
  "The toboggan can only follow a few specific slopes (you opted for a cheaper model
   that prefers rational numbers); start by counting all the trees you would
   encounter for the slope right 3, down 1:"
  [1 3])

(def steps
  "Time to check the rest of the slopes - you need to minimize the probability of
   a sudden arboreal stop, after all.
   
   Determine the number of trees you would encounter if, for each of the following
   slopes, you start at the top-left corner and traverse the map all the way to the 
   bottom:"
  [
    [1 1]
    default-step ;; (This is the slope you already checked.)
    [1 5]
    [1 7]
    [2 1]
  ])

(defn move
  "Simple vector maths"
  [pos step]
  (map + pos step))

(defn check-position
  "The locations you'd check in the above example are marked here with O where
   there was an open square and X where there was a tree:
   The pattern for a line repeats it self infinity"
  [line pos]
  (loop [threes line] ;; as the pattern repeat it selv, we need to repeat it until we are at the position.
;;    (println (count threes) pos)
    (if (> (count threes) pos)      
      (if (= (nth threes pos) \#)
        "X"
        "O")
      (recur (concat threes line)))))
      
(defn check-positions
  "We need to loop through the pattern until we find the end."
  ([input]
      (check-positions input default-step))
  ([input step]
      (check-positions input step start))
  ([input step pos]
;;    (println "check-positions" input step pos)
    (loop [current (move pos step) result []]
;;      (println current (count input) "more: " (> (count input) (first current)))
      (if (<= (count input) (first current))
        result
        (recur (move current step) (conj result (check-position (nth input (first current)) (second current))))))))

(defn part-1
  "Starting at the top-left corner of your map and following a slope of right 3
   and down 1, how many trees would you encounter?"
  ([]
    (part-1 input))
  ([input]
    (part-1 input default-step))
  ([input step]
;;    (println "count-hits" input step)
    (let [hits (frequencies (check-positions input step))]
      (or (hits "X") 0))))

(defn part-2
  "What do you get if you multiply together the number of trees encountered on
   each of the listed slopes?"
  ([]
    (part-2 steps))
  ([input]
    (part-2 input steps))
  ([input steps]
    (loop [items steps results []]
;;      (println "count-all-hits" items results)
      (if (empty? items)
        (apply * results)
        (recur (rest items) (conj results (part-1 input (first items))))))))
        
(defn solve
  ([]
    (solve part-1))
  ([part]
    (solve part input))
  ([part input]
    (part input)))