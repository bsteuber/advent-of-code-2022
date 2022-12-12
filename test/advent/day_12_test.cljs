(ns advent.day-12-test
  (:require [advent.day-12 :as day-12]
            [clojure.test :refer [deftest is]]
            [advent.data :as data]))

(def sample "Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi")

(deftest test-parse
  (let [{:keys [rows cols heights start end]} (day-12/parse-input sample)]
    (is (= [0 0] start))
    (is (= [2 5] end))
    (is (= 5 rows))
    (is (= 8 cols))
    (is (= -1 (get-in heights [0 0])))
    (is (= 26 (get-in heights [2 5])))
    (is (= [-1 0 1 16 15 14 13 12] (get heights 0)))))

(deftest test-neighbours
  (is (= [[0 1] [1 0]]
         (day-12/neighbours [0 0] {:rows 5, :cols 5})))
  (is (= [[2 3] [3 2] [1 2] [2 1]]
         (day-12/neighbours [2 2] {:rows 5, :cols 5})))
  (is (= [[4 4] [3 3] [4 2]]
         (day-12/neighbours [4 3] {:rows 5, :cols 5}))))

(deftest test-flood-once
  (let [data (day-12/parse-input sample)
        after-1 (day-12/flood-once {[2 5] 0} data 0)
        after-2 (day-12/flood-once after-1 data 1)
        after-3 (day-12/flood-once after-2 data 2)]
    (is (= {[2 5] 0, [2 4] 1}
           after-1))
    (is (= {[2 5] 0, [2 4] 1, [1 4] 2}
           after-2))
    (is (= {[2 5] 0, [2 4] 1, [1 4] 2, [1 5] 3}
           after-3))))

(deftest test-part-1
  (is (= 31
         (day-12/part-1 sample))))

#_(deftest run-part-1
    (println "Day 12 Part 1:" (day-12/part-1 data/day-12)))

(deftest test-part-2
  (is (= 29
         (day-12/part-2 sample))))

(deftest run-part-2
  (println "Day 12 Part 2:" (day-12/part-2 data/day-12)))

