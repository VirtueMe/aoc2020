(ns aoc.day06
  "This solves the first and second part of the puzzle for day 6.
  
   See <https://adventofcode.com/2020/day/6>
  
   --- Day 6: Custom Customs ---
   
   As your flight approaches the regional airport where you'll switch to a much
   larger plane, customs declaration forms are distributed to the passengers.

   The form asks a series of 26 yes-or-no questions marked a through z. All you
   need to do is identify the questions for which anyone in your group answers
   \"yes\". Since your group is just you, this doesn't take very long.

   However, the person sitting next to you seems to be experiencing a language barrier and asks if you can help. For each of the people in their group, you write down the questions for which they answer \"yes\", one per line."
  (:gen-class)
  (:require [aoc.core :refer [get-lines join-words]]))

(defn answer-parser
  "Another group asks for your help, then another, and eventually you've
   collected answers from every group on the plane (your puzzle input). Each
   group's answers are separated by a blank line, and within each group,
   each person's answers are on a single line."
  [items]
  (loop [source items current [] results []]
    (if (empty? source)
      (map #(assoc {} :answers (join-words % #"") :count (count %)) (conj results current))
      (let [item (first source)]
        (recur (rest source) (if (empty? item) [] (conj current (str item))) (if (empty? item) (conj results current) results))))))
            
(def input (vec (answer-parser (map #(str %) (get-lines "resources/06.txt")))))

(defn calculate-yes-answers
  "Calculate the sum of answers for the group"
  [input]
  (map #(assoc {} :answers (frequencies (% :answers)) :count (% :count)) input))
  
(defn find-yes-answers
  "What is the sum of those counts?"
  [input]
  (apply + (map count (map #(% :answers) (calculate-yes-answers input)))))
  
(defn filter-group-answers
  "Extract only yes answers from all members"
  [input count]
  (map second (filter #(= (second %) count) input)))
  
(defn find-group-all-yes-answers
  "For each group, count the number of questions to which everyone answered
    \"yes\". What is the sum of those counts?"
  [input]
  (apply + (map count (map #(filter-group-answers (% :answers) (% :count)) (calculate-yes-answers input)))))
  
(defn part-1
  "What is the sum of those counts?"
  ([]
  (part-1 input))
  ([input]
    (find-yes-answers input)))


(defn part-2
  "What is the sum of those counts?"
  ([]
  (part-2 input))
  ([input]
    (find-group-all-yes-answers input)))

