(ns advent.day-11
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (loop [current-monkey nil
         monkeys []
         remaining-lines (->> (str/split-lines input)
                              (remove empty?))]
    (if (seq remaining-lines)
      (let [line-data (->> (str/split (first remaining-lines) " ")
                           (remove empty?)
                           vec)
            next-remaining (next remaining-lines)]
        (case (first line-data)
          "Monkey" (recur (js/parseInt (second line-data)) monkeys next-remaining)
          "Starting" (let [items (->> line-data
                                      (drop 2)
                                      (mapv js/parseInt))]
                       (recur current-monkey
                              (assoc-in monkeys [current-monkey :items] items)
                              next-remaining))
          "Operation:" (let [[_ _ _ x op y] line-data
                             operator (case op
                                        "+" +
                                        "*" *)
                             op-fn (fn [old-value]
                                     (let [x-val (if (= x "old")
                                                   old-value
                                                   (js/parseInt x))
                                           y-val (if (= y "old")
                                                   old-value
                                                   (js/parseInt y))]
                                       (operator x-val y-val)))]
                         (recur current-monkey
                                (assoc-in monkeys [current-monkey :op-fn] op-fn)
                                next-remaining))
          "Test:" (let [[_ _ _ divisor] line-data]
                    (recur current-monkey
                           (assoc-in monkeys [current-monkey :test-divisor] (js/parseInt divisor))
                           next-remaining))
          "If" (let [[_ bool-val _ _ _ target-monkey] line-data
                     key (case bool-val
                           "true:" :test-true
                           "false:" :test-false)]
                 (recur current-monkey
                        (assoc-in monkeys [current-monkey key] (js/parseInt target-monkey))
                        next-remaining))
          (recur current-monkey monkeys next-remaining)))
      monkeys)))
(defn do-turn [monkeys monkey-index do-relief?]
  (let [mod-divisor (->> monkeys
                         (map :test-divisor)
                         (apply *))]
    (loop [state monkeys]
      (let [{:keys [items op-fn test-divisor test-true test-false]} (get state monkey-index)
            next-item (first items)]
        (if next-item
          (let [after-inspect (op-fn next-item)
                after-relief (if do-relief?
                               (quot after-inspect 3)
                               (mod after-inspect mod-divisor))
                target-monkey (if (zero? (mod after-relief test-divisor))
                                test-true
                                test-false)
                next-items (vec (rest items))
                next-state (-> state
                               (assoc-in [monkey-index :items] next-items)
                               (update-in [monkey-index :inspections] (fnil inc 0))
                               (update-in [target-monkey :items] (fn [items]
                                                                   (conj (vec items) after-relief))))]
            (recur next-state))
          state)))))

(defn do-round [start do-relief?]
  (reduce #(do-turn %1 %2 do-relief?) start (range (count start))))

(defn do-n-rounds [start n-rounds do-relief?]
  (->> (iterate #(do-round % do-relief?) start)
       (drop n-rounds)
       first))
(defn part-1 [input]
  (let [monkeys (parse-input input)
        after-20-rounds (do-n-rounds monkeys 20 true)
        [x y] (->> after-20-rounds
                   (map :inspections)
                   (sort >))]
    (* x y)))
(defn part-2 [input]
  (let [monkeys (parse-input input)
        after-10k-rounds (do-n-rounds monkeys 10000 false)
        [x y] (->> after-10k-rounds
                   (map :inspections)
                   (sort >))]
    (* x y)))
