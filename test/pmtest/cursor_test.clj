(ns pmtest.cursor-test
  (:require [clojure.test :refer :all]
            [pmtest.pm.loader :as loader :only :load-pm-file]
            [pmtest.cursor :as c :refer :all]))

(def test-file "examples/test.clj")

(deftest load-creation-and-songs
  (testing "Input setup."
    (let [pm (loader/load-pm-file test-file)
          cursor (:cursor pm)]
      (println "(-> pm :songs first)" (-> pm :songs first)) ; DEBUG
      (println "(:song cursor)" (:song cursor)) ; DEBUG
      (is (= (-> pm :songs first) (:song cursor)))
      (is (= 0 (:song-index cursor))))))
