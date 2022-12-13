(ns advent.day-09
  (:require [cljs.math :as math]
            [clojure.string :as str]))

(def directions {"R" [1 0]
                 "L" [-1 0]
                 "U" [0 -1]
                 "D" [0 1]})

(defn parse-input [input]
  (->> input
       str/split-lines
       (mapcat (fn [line]
                 (let [[direction steps] (str/split line " ")]
                   (repeat (js/parseInt steps)
                           (get directions direction)))))
       vec))

(defn calc-tail-step [head tail]
  (let [diff (map - head tail)]
    (if (some (fn [x]
                (= 2 (js/Math.abs x)))
              diff)
      (mapv math/signum diff)
      [0 0])))

(defn calc-new-tail [new-head tail]
  (let [tail-step (calc-tail-step new-head tail)]
    (mapv + tail tail-step)))

(defn go-direction [[head & knots] direction]
  (let [new-head (mapv + head direction)
        new-knots (reductions calc-new-tail new-head knots)]
    (vec new-knots)))

(defn all-applications [knot-count commands]
  (let [init-state (->> (repeat knot-count [0 0])
                        vec)]
    (reductions go-direction init-state commands)))

(defn count-tail-positions [knot-count input]
  (->> input
       parse-input
       (all-applications knot-count)
       (map last)
       distinct
       count))

(defn part-1 [input]
  (count-tail-positions 2 input))

(defn part-2 [input]
  (count-tail-positions 10 input))
