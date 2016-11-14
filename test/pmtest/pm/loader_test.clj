(ns pmtest.pm.loader-test
  (:require [clojure.test :refer :all]
            [pmtest.pm.loader :as loader :refer :all]))

(def test-file "examples/test.clj")

(deftest load-input-test
  (testing "Input setup."
    (let [pm (loader/load-pm-file test-file)]
      (is (= 2 (count (:inputs pm))))
      (is (= ["input 2" :ws-in "WaveStation"] (second (:inputs pm)))))))

(deftest load-output-test
  (testing "Output setup."
    (let [pm (loader/load-pm-file test-file)]
      (is (= 2 (count (:outputs pm))))
      (is (= ["output 2" :sj "SuperJupiter"] (second (:outputs pm)))))))

(deftest load-messages-test
  (testing "Messages."
    (let [pm (loader/load-pm-file test-file)]
      (is (= 2 (count (:messages pm)))))))

(deftest load-songs-test
  (testing "Songs."
    (let [pm (loader/load-pm-file test-file)]
      (is (= ["First Song" "Second Song" "Third Song"] (map :name (:songs pm)))))))

(deftest load-song-lists-test
  (testing "Song lists."
    (let [pm (loader/load-pm-file test-file)]
      (is (= 2 (count (:song-lists pm))))
      (let [sl (first (:song-lists pm))]
        (is (= "Tonight's Song List") (:name sl))
        (is (= 2 (count (:songs sl))))
        (is (= "First Song" (-> sl :songs first :name)))
        (is (= "Second Song" (-> sl :songs second :name)))))))

(deftest load-cursor-test
  (testing "Cursor."
    (let [pm (loader/load-pm-file test-file)]
      (is (:cursor pm)))))              ; cursor itself is tested elsewhere