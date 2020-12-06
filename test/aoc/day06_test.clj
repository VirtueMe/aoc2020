(ns aoc.day06-test
  (:require [clojure.test :refer [deftest testing is]]
    [aoc.day06 :refer [answer-parser find-yes-answers find-group-all-yes-answers]]))

(def input [
  "abc"
  ""
  "a"
  "b"
  "c"
  ""
  "ab"
  "ac"
  ""
  "a"
  "a"
  "a"
  "a"
  ""
  "b"
  ])

(deftest can-sum-total-yes-answers-test
  (testing "Can calculate the correct count of yes answers"
    (is (= (find-yes-answers (answer-parser input)) 11))))

(deftest can-sum-all-yes-in-group-answers-test
  (testing "Can calculate the correct count of yes answers where group answered
   the same"
    (is (= (find-group-all-yes-answers (answer-parser input)) 6))))
