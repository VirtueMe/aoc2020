(ns aoc.day02
  "This solves the first and second part of the puzzle for day 2.
  
   See <https://adventofcode.com/2020/day/2>
   
   The shopkeeper at the North Pole Toboggan Rental Shop is having a bad day.
   \"Something's wrong with our computers; we can't log in!\" You ask if you
   can take a look.
   
   Their password database seems to be a little corrupted: some of the passwords
   wouldn't have been allowed by the Official Toboggan Corporate Policy that was
   in effect when they were chosen.

   To try to debug the problem, they have created a list (your puzzle input) of
   passwords (according to the corrupted database) and the corporate policy when
   that password was set."
  (:gen-class)
  (:require [aoc.core :refer [get-lines split-words]]))
  
(defn create-password-policy
  "Read password policy record. format [0-9]{1}-[0-9]{1} [a-z]{1}: [a-z]?
  for the first iteration I decided to not use regex."
  [record]
  (let [[rules password] (split-words record #": ") [numbers letter] (split-words rules #" ") [min max] (split-words numbers #"-")]
    { :min (Integer. min) :max (Integer. max) :letter (first letter) :password password }))
    
(def input
  "Fetch the passwords from the input given. The input might not be the same as
   you have, but the solution to solve it will be the same. We are a bit lazy
   and generate the password policy record"
  (vec (map create-password-policy (get-lines "resources/02.txt"))))

(defn validate-policy-01
  "Each line gives the password policy and then the password.
   The password policy indicates the lowest and highest number of times a given 
   letter must appear for the password to be valid. For example, 1-3 a means that
   the password must contain a at least 1 time and at most 3 times."
  [policy]
  (let [list (frequencies (policy :password)) letter (policy :letter)]
    (>= (policy :max) (or (list letter) 0) (policy :min))))

(defn check-position
  "Check if given letter is in given position in password. Extra check to be sure 
   that the password is as long as the position given."
  [password pos letter]
  (if (< (count password) pos)
    false
    (= (nth password pos) letter)))

(defn validate-policy-02
  "Each policy actually describes two positions in the password, where 1 means 
   the first character, 2 means the second character, and so on. (Be careful;
   Toboggan Corporate Policies have no concept of \"index zero\"!) Exactly one
   of these positions must contain the given letter. Other occurrences of the 
   letter are irrelevant for the purposes of policy enforcement."
  [policy]
  (let [letter (policy :letter) password (policy :password)]
;;    (println letter password (policy :min) (policy :max))
    (= (check-position password (- (policy :min) 1) letter) (not (check-position password (- (policy :max) 1) letter)))))
    
(defn policy-validated-count
  "Count how many password policies are correct"
  ([policies]
    (policy-validated-count policies validate-policy-01))
  ([policies validator]
    (let [list (frequencies (map validator policies))]
      (list true))))

(defn part-1
  "How many passwords are valid according to their policies?"
  ([]
    (part-1 input))
  ([input]
    (policy-validated-count input)))

(defn part-2
  "How many passwords are valid according to their correct policies?"
  ([]
    (part-2 input))
  ([input]
    (policy-validated-count input validate-policy-02)))
    
(defn solve
  ([]
    (solve part-1))
  ([part]
    (solve part input))
  ([part input]
    (part input)))
