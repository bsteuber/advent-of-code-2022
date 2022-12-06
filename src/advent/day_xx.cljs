(ns advent.day-xx
  (:require ["fs" :as fs]
            [clojure.set :as set]
            [clojure.string :as str]))

(def sample-lines
  (-> ""
      (str/split "\n")))

(def input-lines
  (-> "resources/day_xx.txt"
      fs/readFileSync
      (str/split "\n")))

(defn parse-input [lines])

(defn solve-part-1 [lines]
  (println "Part 1:"
           (->> lines)))

(defn solve-part-2 [lines]
  (println "Part 2:"
           (->> lines)))

(solve-part-1 sample-lines)
#_(solve-part-2 sample-lines)
