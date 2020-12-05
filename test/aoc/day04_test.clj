(ns aoc.day04-test
  (:require [clojure.test :refer [deftest testing is]]
            [aoc.day04 :refer [creator create-passport validate-passport count-valid-passports get-valid-passports count-valid-rules-passports]]))

(def passportSource
  [
    "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd"
    "byr:1937 iyr:2017 cid:147 hgt:183cm"
    ""
    "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884"
    "hcl:#cfa07d byr:1929"
    ""
    "hcl:#ae17e1 iyr:2013"
    "eyr:2024"
    "ecl:brn pid:760753108 byr:1931"
    "hgt:179cm"
    ""
    "hcl:#cfa07d eyr:2025 pid:166559648"
    "iyr:2011 ecl:brn hgt:59in"
  ])
  
(def passports (map create-passport (creator passportSource)))
  
(deftest can-create-passports-test
  (testing "can create all passports without errror"
    (is (= (count (map create-passport (creator passportSource))))) 4))
  
(deftest validate-valid-passport-testing
  (testing "should validate a valid passport"
    (is (= (validate-passport (first passports)) true))))
    
(deftest invalidate-invalid-passport-testing
  (testing "should invalidate a invalid passport"
    (is (= (validate-passport (second passports)) false))))
      
(deftest count-valid-passports-testing
  (testing "should invalidate a invalid passport"
    (is (= (count-valid-passports passports) 2))))

(deftest count-valid-rules-passports-test
  (testing "should invalidate a invalid passport"
    (is (= (count-valid-rules-passports (get-valid-passports passports)) 1))))
