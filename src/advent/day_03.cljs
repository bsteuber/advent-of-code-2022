(ns advent.day-03
  (:require ["fs" :as fs]
            [clojure.set :as set]
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

(defn find-double [line]
  (let [half-count (/ (count line)
                      2)
        [first-half second-half] (split-at half-count line)]
    (first (set/intersection (into #{} first-half)
                             (into #{} second-half)))))
(def code-a (.charCodeAt "a" 0))


(defn item-priority [letter]
  (let [char-code (.charCodeAt letter 0)
        priority (inc (- char-code code-a))]
    (if (< priority 1)
      (+ priority 58)
      priority)))



(defn solve-part-1 [lines]
  (println "Part 1:"
           (->> lines
                (map find-double)
                (map item-priority)
                (apply +))))

(defn find-badge [lines]
  (->> lines
       (map #(into #{} %))
       (apply set/intersection)
       first))

(defn solve-part-2 [lines]
  (println "Part 2:"
           (->> lines
                (partition 3)
                (map find-badge)
                (map item-priority)
                (apply +))))

(solve-part-1 input-lines)
(solve-part-2 input-lines)
