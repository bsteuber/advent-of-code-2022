(ns advent.day-08
  (:require [clojure.string :as str]
            [advent.data :as data]))

(defn parse-input [input]
  (->> input
       (str/split-lines)
       (mapv (fn [line]
               (mapv js/parseInt line)))))

(def directions [[0 1]
                 [1 0]
                 [0 -1]
                 [-1 0]])

(defn get-width [data]
  (count (first data)))

(defn get-height [data]
  (count data))

(defn go-in-direction [point direction]
  (mapv + point direction))

(defn points-in-line [point direction width height]
  (->> (iterate #(go-in-direction % direction)
                point)
       (drop 1)
       (take-while (fn [[x y]]
                     (and (< -1 x width)
                          (< -1 y height))))
       vec))

(defn all-lines [point width height]
  (mapv #(points-in-line point % width height)
        directions))

(defn get-tree [data [x y]]
  (-> data
      (nth y)
      (nth x)))

(defn visible-from-outside? [data point]
  (let [lines (all-lines point (get-width data) (get-height data))
        tree-height (get-tree data point)]
    (some (fn [line]
            (every? (fn [pt]
                      (< (get-tree data pt)
                         tree-height))
                    line))
          lines)))
(defn part-1 [input]
  (let [data (parse-input input)
        width (get-width data)
        height (get-height data)]
    (->> (for [x (range width)
               y (range height)]
           (visible-from-outside? data [x y]))
         (filter identity)
         count)))
(defn calc-visible-trees-in-line [data line tree-height]
  (->> line
       (take-while (fn [pt]
                     (< (get-tree data pt)
                        tree-height)))
       count
       inc
       (min (count line))))

(defn calc-scenic-score [data point]
  (let [tree-height (get-tree data point)]
    (->> (all-lines point (get-width data) (get-height data))
         (map (fn [line]
                (calc-visible-trees-in-line data line tree-height)))
         (reduce *))))

(defn part-2 [input]
  (let [data (parse-input input)
        width (get-width data)
        height (get-height data)]
    (->> (for [x (range width)
               y (range height)]
           (calc-scenic-score data [x y]))
         (apply max))))
