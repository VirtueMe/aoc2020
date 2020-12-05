(ns aoc.day04
  (:gen-class)
  (:require [aoc.core :refer [get-lines split-words join-words]]))
  
(def passportItems (map #(keyword %) ["byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid" "cid"]))
(def optionalPassportItems (map #(keyword %) ["cid"]))

(defn test-height
  [input & _]
  (let [testheight (re-matcher #"(?<cm>\d{3})cm|(?<in>\d{2})in" input)]
    (if (.matches testheight)
      (let [cm (.group testheight "cm") in (.group testheight "in")]
        (if (nil? cm)
          (>= 76 (Integer. in) 59)
          (>= 193 (Integer. cm) 150)))
      false)))

(defn test-year
  [input rule]
    (let [testyear (re-matcher #"(?<height>\d{4})" input)]
      (and (.matches testyear) (>= (rule :max) (Integer. (.group testyear "height")) (rule :min)))))

(defn test-eye
  [input & _]
    (let [testeye (re-matcher #"amb|blu|brn|gry|grn|hzl|oth" input)]
      (.matches testeye)))

(defn test-hair
  [input & _]
  (let [testhair (re-matcher #"#[a-f0-9]{6}" input)]
    (.matches testhair)))

(defn test-pid
  [input & _]
  (let [testpid (re-matcher #"([0-9]{9})" input)]
    (.matches testpid)))
    
(defn number-validator
  [min max]
  { :min min :max max :matcher test-year})

(defn dummy
  [& _]
  true)
  
(def rules
  {
      :byr (number-validator 1920 2002)
      :iyr (number-validator 2010 2020)
      :eyr (number-validator 2020 2030)
      :hgt { :matcher test-height }
      :hcl { :matcher test-hair }
      :ecl { :matcher test-eye }
      :pid { :matcher test-pid }
      :cid { :matcher dummy }
  })

(defn creator
  [items]
  (loop [source items current [] results []]
    (if (empty? source)
      (map join-words (conj results current))
      (recur (rest source) (if (empty? (first source)) [] (conj current (first source))) (if (empty? (first source)) (conj results current) results)))))

(defn create-passport
  [input]
  (let [elements (map #(split-words % #":") (split-words input))]
    (apply merge (map #(hash-map (keyword (first %)) (second %)) elements))))
    
(def input (vec (map create-passport (creator (get-lines "resources/04.txt")))))

(defn validate-passport
  [passport]
  (let [missing (filter #(not (contains? passport %)) passportItems)]
    (if (empty? missing)
      true
      (empty? (filter #(not (.contains (vec optionalPassportItems) %)) missing)))))
      
(defn rule-validation
  "validates passports according to our rules"
  [input]
  (loop [items (seq input)]
    (if (empty? items)
      true
      (let [item (first items) rule (rules (first item))]
        (if (not ((rule :matcher) (second item) rule))
          false
          (recur (rest items)))))))

(defn get-valid-passports
  [passports]
  (filter validate-passport passports))

(defn count-valid-passports 
  [passports]
  (count (get-valid-passports passports)))

(defn get-valid-rules-passports
  [passports]
  (filter rule-validation passports))

(defn count-valid-rules-passports
  [passports]
  (count (get-valid-rules-passports passports)))

(defn part-1
  ([]
    (part-1 input))
  ([input]
    (count-valid-passports input)))
  
(defn part-2
  ([]
    (part-1 input))
  ([input]
    (count-valid-rules-passports (get-valid-passports input))))

(defn solve
  ([]
    (solve part-1))
  ([part]
    (solve part input))
  ([part input]
    (part input)))