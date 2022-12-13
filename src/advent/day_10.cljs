(ns advent.day-10
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> input
       str/split-lines
       (mapcat (fn [line]
                 (let [[cmd arg] (str/split line " ")]
                   (case cmd
                     "noop" [0]
                     "addx" [0 (js/parseInt arg)]))))
       vec))
(defn register-values [numbers]
  (vec (reductions + 1 numbers)))

(defn signal-strenghs [values]
  (->> (range 19 10000000 40)
       (map (fn [index]
              (when-let [value (get values index)]
                (* (inc index) value))))
       (take-while some?)
       vec))

(defn part-1 [input]
  (->> input
       parse-input
       register-values
       signal-strenghs
       (apply +)))

(defn render-image [register-values]
  (->> register-values
       (map-indexed (fn [i x]
                      (if (<= (dec x) (mod i 40) (inc x))
                        "#"
                        ".")))
       (partition 40)
       (map str/join)
       (str/join "\n")))

(defn part-2 [input]
  (->> input
       parse-input
       register-values
       render-image))
