(ns advent.day-04
  (:require ["fs" :as fs]
            [clojure.set :as set]
            [clojure.string :as str]))

(def sample-lines
  (-> "2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8"
      (str/split "\n")))

(def input-lines
  (-> "resources/day_04.txt"
      fs/readFileSync
      (str/split "\n")))

(defn parse-elf-sections [elf]
  (let [[start end] (str/split elf "-")]
    (into #{}
          (range (js/parseInt start)
                 (inc (js/parseInt end))))))

(defn parse-sections [line]
  (mapv parse-elf-sections (str/split line ",")))

(defn contains-completely? [[set-1 set-2]]
  (let [shared (set/intersection set-1 set-2)]
    (or (= shared set-1)
        (= shared set-2))))

(defn overlaps? [[set-1 set-2]]
  (boolean (seq (set/intersection set-1 set-2))))

(defn solve-part-1 [lines]
  (println "Part 1:"
           (->> lines
                (map parse-sections)
                (map contains-completely?)
                (filter identity)
                count)))



(defn solve-part-2 [lines]
  (println "Part 2:"
           (->> lines
                (map parse-sections)
                (map overlaps?)
                (filter identity)
                count)))

(solve-part-1 input-lines)
(solve-part-2 input-lines)
