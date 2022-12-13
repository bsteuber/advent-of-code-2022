(ns advent.day-09-test
  (:require [advent.day-09 :as day-09]
            [clojure.test :refer [deftest is]]
            [advent.data :as data]))

(def sample-1 "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2")

(def sample-2 "R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20")

(deftest test-parse-input
  (is (= (take 12 (day-09/parse-input sample-1))
         [[1 0] [1 0] [1 0] [1 0]
          [0 -1] [0 -1] [0 -1] [0 -1]
          [-1 0] [-1 0] [-1 0]
          [0 1]])))

(deftest test-calc-tail-step
  (is (= (day-09/calc-tail-step [1 0] [0 0])
         [0 0]))
  (is (= (day-09/calc-tail-step [1 1] [0 0])
         [0 0]))
  (is (= (day-09/calc-tail-step [2 0] [0 0])
         [1 0]))
  (is (= (day-09/calc-tail-step [2 1] [0 0])
         [1 1])))

(deftest test-all-applications
  (is (= (day-09/all-applications 2 (take 8 (day-09/parse-input sample-1)))
         [[[0 0] [0 0]]
          [[1 0] [0 0]]
          [[2 0] [1 0]]
          [[3 0] [2 0]]
          [[4 0] [3 0]]
          [[4 -1] [3 0]]
          [[4 -2] [4 -1]]
          [[4 -3] [4 -2]]
          [[4 -4] [4 -3]]])))

(deftest test-part-1
  (is (= (day-09/part-1 sample-1)
         13)))

(deftest run-part-1
  (println "Day 09 Part 1:" (day-09/part-1 data/day-09)))

(deftest test-part-2
  (is (= (day-09/part-2 sample-1)
         1))
  (is (= (day-09/part-2 sample-2)
         36)))

(deftest run-part-2
  (println "Day 09 Part 2:" (day-09/part-2 data/day-09)))

