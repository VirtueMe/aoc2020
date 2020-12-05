(ns aoc.day04
  "This solves the first and second part of the puzzle for day 4.
  
   See <https://adventofcode.com/2020/day/4>
   
   You arrive at the airport only to realize that you grabbed your North Pole
   Credentials instead of your passport. While these documents are extremely
   similar, North Pole Credentials aren't issued by a country and therefore
   aren't actually valid documentation for travel in most of the world.

   It seems like you're not the only one having problems, though; a very long
   line has formed for the automatic passport scanners, and the delay could
   upset your travel itinerary.

   Due to some questionable network security, you realize you might be able to
   solve both of these problems at the same time.
   "
  (:gen-class)
  (:require [aoc.core :refer [get-lines split-words join-words]]))
  
(def passportItems
  "The automatic passport scanners are slow because they're having trouble
   detecting which passports have all required fields. The expected fields are
   as follows:

   - byr (Birth Year)
   - iyr (Issue Year)
   - eyr (Expiration Year)
   - hgt (Height)
   - hcl (Hair Color)
   - ecl (Eye Color)
   - pid (Passport ID)
   - cid (Country ID)"
  (map #(keyword %) ["byr" "iyr" "eyr" "hgt" "hcl" "ecl" "pid" "cid"]))
  
(def optionalPassportItems
  "If a passport is missing field cid, it looks like data from North Pole
   Credentials, not a passport at all! Surely, nobody would mind if you made
   the system temporarily ignore missing cid fields. Treat this \"passport\" as
   valid."
  (map keyword ["cid"]))

(defn passport-parser
  "Passport data is validated in batch files (your puzzle input). Each passport
   is represented as a sequence of key:value pairs separated by spaces or
   newlines. Passports are separated by blank lines."
  [items]
  (loop [source items current [] results []]
    (if (empty? source)
      (map join-words (conj results current))
      (recur (rest source) (if (empty? (first source)) [] (conj current (first source))) (if (empty? (first source)) (conj results current) results)))))

(defn create-passport
  [input]
  (let [elements (map #(split-words % #":") (split-words input))]
    (apply merge (map #(hash-map (keyword (first %)) (second %)) elements))))
    
(def input (vec (map create-passport (passport-parser (get-lines "resources/04.txt")))))

(defn validate-passport
  "In your batch file, how many passports are valid?"
  [passport]
  (let [missing (filter #(not (contains? passport %)) passportItems)]
    (if (empty? missing)
      true
      (empty? (filter #(not (.contains (vec optionalPassportItems) %)) missing)))))

(defn get-valid-passports
  [passports]
  (filter validate-passport passports))

(defn count-valid-passports 
  [passports]
  (count (get-valid-passports passports)))

(defn test-height
  "hgt (Height) - a number followed by either cm or in:
    - If cm, the number must be at least 150 and at most 193.
    - If in, the number must be at least 59 and at most 76."
  [input & _]
  (let [testheight (re-matcher #"(?<cm>\d{3})cm|(?<in>\d{2})in" input)]
    (if (.matches testheight)
      (let [cm (.group testheight "cm") in (.group testheight "in")]
        (if (nil? cm)
          (>= 76 (Integer. in) 59)
          (>= 193 (Integer. cm) 150)))
      false)))

(defn test-year
  "Rules for a year four digits; at least :min and at most :max."
  [input rule]
    (let [testyear (re-matcher #"(?<height>\d{4})" input)]
      (and (.matches testyear) (>= (rule :max) (Integer. (.group testyear "height")) (rule :min)))))

(defn test-eye
  "(Eye Color) - exactly one of: amb blu brn gry grn hzl oth."
  [input & _]
    (let [testeye (re-matcher #"amb|blu|brn|gry|grn|hzl|oth" input)]
      (.matches testeye)))

(defn test-hair
  "(Hair Color) - a # followed by exactly six characters 0-9 or a-f."
  [input & _]
  (let [testhair (re-matcher #"#[a-f0-9]{6}" input)]
    (.matches testhair)))

(defn test-pid
  "(Passport ID) - a nine-digit number, including leading zeroes."
  [input & _]
  (let [testpid (re-matcher #"([0-9]{9})" input)]
    (.matches testpid)))
    
(defn year-validator
  [min max]
  { :min min :max max :matcher test-year})

(defn dummy
  [& _]
  true)
  
(def rules
  "You can continue to ignore the cid field, but each other field has strict
   rules about what values are valid for automatic validation:
   
   - byr (Birth Year) - four digits; at least 1920 and at most 2002.
   - iyr (Issue Year) - four digits; at least 2010 and at most 2020.
   - eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
   - hgt (Height) - a number followed by either cm or in:
      - If cm, the number must be at least 150 and at most 193.
      - If in, the number must be at least 59 and at most 76.
   - hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
   - ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
   - pid (Passport ID) - a nine-digit number, including leading zeroes.
   - cid (Country ID) - ignored, missing or not."
  {
      :byr (year-validator 1920 2002)
      :iyr (year-validator 2010 2020)
      :eyr (year-validator 2020 2030)
      :hgt { :matcher test-height }
      :hcl { :matcher test-hair }
      :ecl { :matcher test-eye }
      :pid { :matcher test-pid }
      :cid { :matcher dummy }
  })

      
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

(defn get-valid-rules-passports
  [passports]
  (filter rule-validation passports))

(defn count-valid-rules-passports
  [passports]
  (count (get-valid-rules-passports passports)))

(defn part-1
  "Count the number of valid passports - those that have all required fields.
   Treat cid as optional. In your batch file, how many passports are valid?"
  ([]
    (part-1 input))
  ([input]
    (count-valid-passports input)))
  
(defn part-2
  "Count the number of valid passports - those that have all required fields and
   valid values. Continue to treat cid as optional. In your batch file, how many
   passports are valid?"
  ([]
    (part-2 input))
  ([input]
    (count-valid-rules-passports (get-valid-passports input))))

(defn solve
  ([]
    (solve part-1))
  ([part]
    (solve part input))
  ([part input]
    (part input)))