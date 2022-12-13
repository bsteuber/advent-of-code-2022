(ns advent.day-08-test
  (:require [advent.day-08 :as day-08]
            [clojure.test :refer [deftest is]]
            [advent.data :as data]))

(def sample "30373
25512
65332
33549
35390")

(deftest test-parse
  (is (= [[2 5 5]
          [1 3 4]]
         (day-08/parse-input "255\n134"))))

(deftest test-dimensions
  (let [data (day-08/parse-input "255\n134")]
    (is (= 3 (day-08/get-width data)))
    (is (= 2 (day-08/get-height data)))))

(deftest test-go-in-direction
  (is (= (day-08/go-in-direction [2 3] [1 0])
         [3 3]))
  (is (= (day-08/go-in-direction [2 3] [0 -1])
         [2 2])))

(deftest test-points-in-line
  (is (= (day-08/points-in-line [2 1] [0 1] 5 6)
         [[2 2] [2 3] [2 4] [2 5]])))

(deftest test-all-lines
  (is (= (day-08/all-lines [2 1] 4 3)
         [[[2 2]]
          [[3 1]]
          [[2 0]]
          [[1 1]
           [0 1]]])))

(deftest test-visible
  (let [data (day-08/parse-input sample)]
    (is (day-08/visible-from-outside? data [0 3]))
    (is (day-08/visible-from-outside? data [1 1]))
    (is (not (day-08/visible-from-outside? data [3 1])))))

(deftest test-part-1
  (is (= (day-08/part-1 sample)
         21)))

(deftest run-part-1
  (println "Day 08 Part 1:" (day-08/part-1 data/day-08)))

(deftest test-scenic-score
  (let [data (day-08/parse-input sample)]
    (is (= (day-08/calc-scenic-score data [2 1])
           4))
    (is (= (day-08/calc-scenic-score data [2 3])
           8))))

(deftest test-part-2
  (is (= (day-08/part-2 sample)
         8)))

(deftest run-part-2
  (println "Day 08 Part 2:" (day-08/part-2 data/day-08)))
