(ns aoc.day02-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc.core :refer [split-words]]
            [aoc.day02 :refer [create-password-policy validate-policy-01 validate-policy-02 policy-validated-count]]))

(def input
  [
    "1-3 a: abcde"
    "1-3 b: cdefg"
    "2-9 c: ccccccccc"
  ])
  
(def records
  (map create-password-policy input))
  
(deftest split-input-test
  (testing "Check that it splits up the word correct"
    (is (= (split-words (first input) #": ") ["1-3 a" "abcde"]))))
    
(deftest create-record-test
  (testing "Should create record from string"
    (is (= (create-password-policy (first input)) { :min 1 :max 3 :letter \a :password "abcde" }))))
    
(deftest validate-01-test 
  (testing "Should validate password"
    (is (= (validate-policy-01 (create-password-policy (first input))) true))))
    
(deftest validate-02-test 
  (testing "Should validate password"
    (is (= (validate-policy-02 (create-password-policy (first input))) true))))
      
(deftest validate-count-test 
  (testing "Should find count of validated passwords"
    (is (= (policy-validated-count records) 2))))
    