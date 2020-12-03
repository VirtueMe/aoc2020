(ns aoc.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc.core :refer [split-words]]
            [aoc.day02 :refer [create-record validate-01 validate-02 validate-count]]))

(def input
  [
    "1-3 a: abcde"
    "1-3 b: cdefg"
    "2-9 c: ccccccccc"
  ])
  
(def records
  (map #(create-record %) input))
  
(deftest split-input
  (testing "Check that it splits up the word correct"
    (is (= (split-words (first input) #": ") ["1-3 a" "abcde"]))))
    
(deftest create-record-test
  (testing "Should create record from string"
    (is (= (create-record (first input)) { :min 1 :max 3 :letter \a :password "abcde" }))))
    
(deftest validate-01-test 
  (testing "Should validate password"
    (is (= (validate-01 (create-record (first input))) true))))
    
(deftest validate-02-test 
  (testing "Should validate password"
    (is (= (validate-02 (create-record (first input))) true))))
      
(deftest validate-count-test 
  (testing "Should find count of validated passwords"
    (is (= (validate-count records) 2))))
    