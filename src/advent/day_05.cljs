(ns advent.day-05
  (:require ["fs" :as fs]
            [clojure.set :as set]
            [clojure.string :as str]))

(def sample-lines
  (-> "    [D]
[N] [C]
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2"
      (str/split "\n")))

(def input-lines
  (-> "resources/day_05.txt"
      fs/readFileSync
      (str/split "\n")))

(defn parse-start-line [line]
  (->> line
       (partition 4 4 " ")
       (map second)))

(defn parse-start [lines]
  (let [letters (->> lines
                     butlast
                     (map parse-start-line))
        tmp-state (atom {})]
    (doseq [layer (reverse letters)]
      (doseq [[index letter] (map-indexed vector layer)]
        (when-not (= letter " ")
          (let [stack (inc index)]
            (swap! tmp-state update stack conj letter)))))
    @tmp-state))

(defn parse-commands [lines]
  (for [line lines]
    (let [[_ num-items _ from-stack _ to-stack] (str/split line " ")]
      {:num-items num-items
       :from-stack (js/parseInt from-stack)
       :to-stack (js/parseInt to-stack)})))

(defn parse-input [lines]
  (let [[start _ commands] (partition-by empty? lines)]
    {:start (parse-start start)
     :commands (parse-commands commands)}))

(defn apply-command-part-1 [state {:keys [num-items from-stack to-stack]}]
  (let [tmp-state (atom state)]
    (dotimes [_ num-items]
      (let [item (peek (get @tmp-state from-stack))]
        (swap! tmp-state update from-stack pop)
        (swap! tmp-state update to-stack conj item)))
    @tmp-state))

(defn apply-commands-part-1 [state commands]
  (reduce apply-command-part-1 state commands))

(defn apply-command-part-2 [state {:keys [num-items from-stack to-stack]}]
  (let [tmp-state (atom state)]
    (let [items (take num-items (get @tmp-state from-stack))]
      (doseq [item (reverse items)]
        (swap! tmp-state update from-stack pop)
        (swap! tmp-state update to-stack conj item)))
    @tmp-state))

(defn apply-commands-part-2 [state commands]
  (reduce apply-command-part-2 state commands))

(defn list-top-items [state]
  (->> state
       (sort-by first)
       (map (comp peek second))
       (apply str)))

(defn solve-part-1 [lines]
  (let [{:keys [start commands]} (parse-input lines)]
    (println "Part 1:"
             (list-top-items
              (apply-commands-part-1 start commands)))))

(defn solve-part-2 [lines]
  (let [{:keys [start commands]} (parse-input lines)]
    (println "Part 2:"
             (list-top-items
              (apply-commands-part-2 start commands)))))

(solve-part-1 input-lines)
(solve-part-2 input-lines)
