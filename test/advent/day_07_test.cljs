(ns advent.day-07-test
  (:require [advent.data :as data]
            [advent.day-07 :as day-07]
            [clojure.test :refer-macros [deftest is]]))

(def sample
  "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k")

(deftest test-part-1
  (is (= (day-07/part-1 sample)
         95437)))

(deftest run-part-1
  (println "Day 07 part 1:" (day-07/part-1 data/day-07)))

(deftest test-part-2
  (is (= (day-07/part-2 sample)
         24933642)))

(deftest run-part-2
  (println "Day 07 part 2:" (day-07/part-2 data/day-07)))

