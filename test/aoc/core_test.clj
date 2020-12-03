(ns aoc.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc.core :refer [split-words]]))

(deftest split-words-test
  (testing "Should split words correctly"
    (is (= (split-words "Hello test") ["Hello", "test"]))))
