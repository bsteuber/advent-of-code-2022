(ns day-02
  (:require ["fs" :as fs]
            [clojure.string :as str]))

(def sample-lines
  ["vJrwpWtwJgWrhcsFMMfFFhFp"
   "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
   "PmmdzqPrVvPwwTWBwg"
   "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
   "ttgJtRGJQctTZtZT"
   "CrZsJsPPZsGzwwsLwLmpwMDw"])

(def input-lines
  (-> "resources/day_03.txt"
      fs/readFileSync
      (str/split "\n")))

(defn solve-part-1 [lines])

(prn solve-part-1 sample-lines)
