(ns aoc.day03
  (:gen-class)
  (:require [aoc.core :refer [get-lines]]))

(def input (vec (map #(str %) (get-lines "resources/03.txt"))))

(def start [0 0])
(def default-step [1 3])

(def steps
  [
    [1 1]
    default-step
    [1 5]
    [1 7]
    [2 1]
  ])

(defn move
  [pos step]
  (map + pos step))

(defn check-position
  [line pos]
  (loop [threes line]
;;    (println (count threes) pos)
    (if (> (count threes) pos)      
      (if (= (nth threes pos) \#)
        "X"
        "O")
      (recur (concat threes line)))))
      
(defn check-positions
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

(defn count-hits
  ([]
    (count-hits input))
  ([input]
    (count-hits input default-step))
  ([input step]
;;    (println "count-hits" input step)
    (let [hits (frequencies (check-positions input step))]
      (or (hits "X") 0))))

(defn count-all-hits
  ([]
    (count-all-hits steps))
  ([input]
    (count-all-hits input steps))
  ([input steps]
    (loop [items steps results []]
;;      (println "count-all-hits" items results)
      (if (empty? items)
        (apply * results)
        (recur (rest items) (conj results (count-hits input (first items))))))))
        
(defn solve
  ([]
    (solve count-hits))
  ([part]
    (solve part input))
  ([part input]
    (part input)))