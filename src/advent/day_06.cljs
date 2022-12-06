(ns advent.day-06
  (:require ["fs" :as fs]
            [clojure.set :as set]
            [clojure.string :as str]))

(def sample-1 "mjqjpqmgbljsphdztnvjfqwrcgsmlb")
(def sample-2 "bvwbjplbgvbhsrlpgdmjqwftvncz")
(def sample-3 "nppdvjthqldpwncqszvftbrmjlhg")
(def sample-4 "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")
(def sample-5 "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")

(def input
  (-> "resources/day_06.txt"
      fs/readFileSync))

(defn detect-start-of-packet [data]
  (->> data
       (partition 4 1)
       (map-indexed (fn [index chars]
                      (when (= (count (distinct chars))
                               4)
                        (+ index 4))))
       (some identity)))

(defn detect-start-of-message [data]
  (->> data
       (partition 14 1)
       (map-indexed (fn [index chars]
                      (when (= (count (distinct chars))
                               14)
                        (+ index 14))))
       (some identity)))

(defn solve-part-1 [input]
  (println "Part 1:"
           (->> input
                detect-start-of-packet)))

(defn solve-part-2 [input]
  (println "Part 2:"
           (->> input
                detect-start-of-message)))

;; (solve-part-1 sample-1)
;; (solve-part-1 sample-2)
;; (solve-part-1 sample-3)
;; (solve-part-1 sample-4)
;; (solve-part-1 sample-5)
(solve-part-1 input)
;; (solve-part-2 sample-1)
;; (solve-part-2 sample-2)
;; (solve-part-2 sample-3)
;; (solve-part-2 sample-4)
;; (solve-part-2 sample-5)
(solve-part-2 input)
