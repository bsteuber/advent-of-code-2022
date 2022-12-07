(ns advent.day-07
  (:require [clojure.string :as str]
            [clojure.walk :as walk]))

(defn parse-input [input]
  (loop [path []
         tree {}
         lines (str/split input "\n")]
    (if-let [lines (seq lines)]
      (let [[x y z] (str/split (first lines) " ")
            remaining (next lines)]
        (case x
          "$" (case y
                "cd" (case z
                       "/" (recur [] tree remaining)
                       ".." (recur (pop path) tree remaining)
                       (recur (conj path z) tree remaining))
                "ls" (recur path tree remaining))
          "dir" (recur path tree remaining)
          (recur path (assoc-in tree (conj path y) (js/parseInt x)) remaining)))
      tree)))

(defn calc-dir-size [dir]
  (walk/postwalk
   (fn [x]
     (if (map? x)
       (->> x
            (map second)
            (apply +))
       x))
   dir))

(defn calc-dir-sizes [dir]
  (let [a-sizes (atom [])]
    (walk/postwalk
     (fn [x]
       (when (map? x)
         (swap! a-sizes conj (calc-dir-size x)))
       x)
     dir)
    @a-sizes))

(defn part-1 [input]
  (let [tree (parse-input input)]
    (->> tree
         calc-dir-sizes
         (filter #(<= % 100000))
         (apply +))))

(defn part-2 [input]
  (let [dir-sizes (->> input
                       parse-input
                       calc-dir-sizes)
        total-mem 70000000
        required-mem 30000000
        used-mem (last dir-sizes)
        free-mem (- total-mem used-mem)
        delete-mem (- required-mem free-mem)]
    (->> dir-sizes
         sort
         (some (fn [x]
                 (when (>= x delete-mem)
                   x))))))
