(ns aoc.day01-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc.day01 :refer [find-numbers-advanced find-numbers sum-numbers goal]]))

(def tuple [1721 299])

(def tres [979 366 675])

(def input [1721 979 366 299 675 1456])
(deftest find-numbers-test
  (testing "Should return correct tuple from array"
    (is (= (find-numbers input goal) tuple))))

(deftest sum-numbers-tuple-test
  (testing "Should return correct sum from tuple"
    (is (= (sum-numbers tuple) 514579))))

(deftest sum-numbers-tres-test
  (testing "Should return correct sum from tuple"
    (is (= (sum-numbers tres) 241861950))))

(deftest find-numbers-advanced-test
  (testing "Should find the correct three numbers that sums up to 2020"
    (is (= (find-numbers-advanced input goal) tres))))