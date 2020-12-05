(ns aoc.day03-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc.day03 :refer [check-position check-positions move part-1 part-2 default-step steps]]))

(def pattern
  [
    "..##......."
    "#...#...#.."
    ".#....#..#."
    "..#.#...#.#"
    ".#...##..#."
    "..#.##....."
    ".#.#.#....#"
    ".#........#"
    "#.##...#..."
    "#...##....#"
    ".#..#...#.#"
  ])

(deftest move-single-test
  (testing "Should move correctly 1 step"
    (is (= (move [0 0] default-step) default-step))))

(deftest move-test
  (testing "Should move correctly multiple step"
    (is (= (move (move [0 0] default-step) default-step) [2 6]))))
  
(deftest check-position-hit-test
  (testing "Should return X on hit"
    (is (= (check-position (first pattern) 2) "X"))))

(deftest check-position-miss-test
  (testing "Should return O on miss"
    (is (= (check-position (first pattern) 1) "O"))))
    

(deftest check-position-length-hit-test
  (testing "Should return X on hit passed intial length"
    (is (= (check-position (first pattern) 13) "X"))))


(deftest check-position-length-miss-test
  (testing "Should return O on miss passed intial length"
    (is (= (check-position (first pattern) 16) "O"))))

(deftest check-positions-test
  (testing "Should return the correct pattern"
    (is (= (check-positions pattern) ["O" "X" "O" "X" "X" "O" "X" "X" "X" "X"]))))
    
(deftest count-hits-default-test
  (testing "Should return the correct number of hits"
    (is (= (part-1 pattern) 7))))
    
(deftest count-hits-alternative-test
  (testing "Should return the correct number of hits"
    (is (= (part-1 pattern (first steps)) 2))))
    
(deftest count-hits-line-2-test
  (testing "Should return the correct number of hits"
    (is (= (part-1 pattern [1 5]) 3))))
        
(deftest count-hits-line-3-test
  (testing "Should return the correct number of hits"
    (is (= (part-1 pattern [1 7]) 4))))
      
(deftest count-hits-line-4-test
  (testing "Should return the correct number of hits"
    (is (= (part-1 pattern [2 1]) 2))))

(deftest count-all-hits-default-test
  (testing "Should return the correct number of all hits"
    (is (= (part-2 pattern steps) 336))))
