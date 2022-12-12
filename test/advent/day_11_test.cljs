(ns advent.day-11-test
  (:require [advent.day-11 :as day-11]
            [clojure.test :refer [deftest is]]
            [advent.data :as data]))

(def sample "Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1")

(deftest test-parse
  (let [monkeys (day-11/parse-input sample)]
    (is (= 4
           (count monkeys)))
    (is (= [[79 98] [54 65 75 74] [79 60 97] [74]]
           (mapv :items monkeys)))
    (is (= [1900 106 10000 103]
           (->> monkeys
                (map :op-fn)
                (mapv (fn [op-fn]
                        (op-fn 100))))))
    (is (= [23 19 13 17]
           (->> monkeys
                (mapv :test-divisor))))
    (is (= [2 2 1 0]
           (->> monkeys
                (mapv :test-true))))
    (is (= [3 0 3 1]
           (->> monkeys
                (mapv :test-false))))))

(deftest test-turn
  (let [start (day-11/parse-input sample)
        after-0 (day-11/do-turn start 0 true)
        after-1 (day-11/do-turn after-0 1 true)
        after-2 (day-11/do-turn after-1 2 true)
        after-3 (day-11/do-turn after-2 3 true)]
    (is (= [[20 23 27 26] [2080 25 167 207 401 1046] [] []]
           (mapv :items after-3)))))

(deftest test-round
  (let [start (day-11/parse-input sample)
        after-0 (day-11/do-round start true)
        after-1 (day-11/do-round after-0 true)]
    (is (= [[20 23 27 26] [2080 25 167 207 401 1046] [] []]
           (mapv :items after-0)))
    (is (= [[695 10 71 135 350] [43 49 58 55 362] [] []]
           (mapv :items after-1)))))

(deftest test-n-rounds
  (let [start (day-11/parse-input sample)]
    (is (= [[20 23 27 26] [2080 25 167 207 401 1046] [] []]
           (mapv :items (day-11/do-n-rounds start 1 true))))
    (is (= [[695 10 71 135 350] [43 49 58 55 362] [] []]
           (mapv :items (day-11/do-n-rounds start 2 true))))
    (is (= [[10 12 14 26 34] [245 93 53 199 115] [] []]
           (mapv :items (day-11/do-n-rounds start 20 true))))))

(deftest test-total-inspections
  (let [start (day-11/parse-input sample)]
    (is (= [101 95 7 105]
           (mapv :inspections (day-11/do-n-rounds start 20 true))))
    (is (= [99 97 8 103]
           (mapv :inspections (day-11/do-n-rounds start 20 false))))))

(deftest test-part-1
  (is (= 10605
         (day-11/part-1 sample))))

(deftest run-part-1
  (println "Day 11 Part 1:" (day-11/part-1 data/day-11)))

(deftest test-part-2
  (is (= 2713310158
         (day-11/part-2 sample))))

(deftest run-part-2
  (println "Day 11 Part 2:" (day-11/part-2 data/day-11)))
