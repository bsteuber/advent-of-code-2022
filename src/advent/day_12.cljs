(ns advent.day-12
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (let [tmp-atom (atom {})
        heights (->> input
                     str/split-lines
                     (map-indexed (fn [row-index line]
                                    (->> line
                                         (map-indexed (fn [col-index ch]
                                                        (case ch
                                                          "S" (do (swap! tmp-atom assoc :start [row-index col-index])
                                                                  -1)
                                                          "E" (do (swap! tmp-atom assoc :end [row-index col-index])
                                                                  (inc (- (.charCodeAt "z" 0)
                                                                          (.charCodeAt "a" 0))))
                                                          (- (.charCodeAt ch 0)
                                                             (.charCodeAt "a" 0)))))
                                         vec)))
                     vec)]
    (merge @tmp-atom
           {:heights heights
            :rows (count heights)
            :cols (count (first heights))})))


(defn neighbours [point {:keys [rows cols]}]
  (->> [[0 1] [1 0] [-1 0] [0 -1]]
       (map #(mapv + point %))
       (filterv (fn [[row col]]
                  (and (< -1 row rows)
                       (< -1 col cols))))))

(defn flood-once [distances data iteration]
  (->> distances
       (filter (fn [[_ distance]]
                 (= distance iteration)))
       (map (fn [[[row col :as point] distance]]
              (let [point-height (get-in data [:heights row col])]
                (->> (neighbours point data)
                     (map (fn [[neighb-row neighb-col :as neighb]]
                            (let [neighb-height (get-in data [:heights neighb-row neighb-col])]
                              (when (and (not (get distances neighb))
                                         (>= neighb-height (dec point-height)))
                                [neighb (inc distance)]))))
                     (filter some?)
                     (into {})))))
       (apply merge-with min distances)))

(defn flood-all [data]
  (let [{:keys [start end]} data]
    (loop [distances {end 0}
           counter 0]
      (let [next-distances (flood-once distances data counter)]
        (if (= next-distances distances)
          distances
          (recur next-distances (inc counter)))))))

(defn part-1 [input]
  (let [data (parse-input input)
        distances (flood-all data)]
    (get distances (:start data))))

(defn part-2 [input]
  (let [data (parse-input input)
        distances (flood-all data)]
    (->> (:heights data)
         (map-indexed (fn [row-index row]
                        (->> row
                             (map-indexed (fn [col-index height]
                                            (when (zero? height)
                                              [row-index col-index]))))))
         (apply concat)
         (filter some?)
         (map distances)
         (filter some?)
         (apply min))))
