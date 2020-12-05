(ns aoc.day05-test
  (:require [clojure.test :refer [deftest testing is]]
    [aoc.day05 :refer [calc-seat-id]]))

(deftest can-create-calculate-correct-id
  (testing "can create correct unique seat it"
    (is (= (calc-seat-id "BFFFBBFRRR") 567))))

