(ns day-02
  (:require ["fs" :as fs]
            [clojure.string :as str]))

(defn score-line-1 [line]
  (let [[opp my] (str/split line " ")
        shape-score (case my
                      "X" 1
                      "Y" 2
                      "Z" 3)
        opp-num (case opp
                  "A" 1
                  "B" 2
                  "C" 3)
        game-score (case (mod (- shape-score opp-num)
                              3)
                     0 3
                     1 6
                     2 0)]
    (+ shape-score game-score)))

(defn score-line-2 [line]
  (let [[opp end] (str/split line " ")
        end-diff (case end
                   "X" 2
                   "Y" 0
                   "Z" 1)
        opp-num (case opp
                  "A" 1
                  "B" 2
                  "C" 3)

        my (case (mod (+ end-diff opp-num)
                      3)
             0 "Z"
             1 "X"
             2 "Y")]
    (score-line-1 (str opp " " my))))

(defn run []
  (let [input-str (fs/readFileSync "resources/day_02.txt")
        lines (str/split input-str "\n")
        answer-1 (->> lines
                      (map score-line-1)
                      (apply +))
        answer-2 (->> lines
                      (map score-line-2)
                      (apply +))]
    (println "Answer 1:" answer-1)
    (println "Answer 2:" answer-2)))

(run)
