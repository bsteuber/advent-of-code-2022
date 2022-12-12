(ns main.tests
  (:require [cljs.test :refer [run-tests]]
            [cljs-test-display.core :as test-display]
            [advent.day-12-test]))

(defn run []
  (js/console.clear)
  (run-tests
   (test-display/init! "app")
   'advent.day-12-test))
