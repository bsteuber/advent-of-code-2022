(ns advent.day-01
  (:require ["fs" :as fs]
            [clojure.string :as str]))

(defn -main []
  (let [input-str (fs/readFileSync "resources/day_01.txt")
        lines (str/split input-str "\n")
        blocks (->> lines
                    (partition-by empty?)
                    (remove (comp empty? first))
                    (map (fn [lines]
                           (map #(js/parseInt %) lines))))
        sums (->> blocks
                  (map #(apply + %)))
        answer-1 (apply max sums)
        answer-2 (->> sums
                      (sort >)
                      (take 3)
                      (apply +))]
    (println "Answer 1:" answer-1)
    (println "Answer 2:" answer-2)))
