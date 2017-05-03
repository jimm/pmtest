(ns pmtest.pm.loader-test
  (:require [clojure.test :refer :all]
            [pmtest.pm.loader :as loader :refer :all]))

(def test-file "examples/test.clj")

(deftest load-input-test
  (testing "Input setup."
    (let [pm (loader/load-pm-file test-file)]
      (is (= {:mb {:type :input :sym :mb :port-name "input 1" :display-name "midiboard"}
              :ws-in {:type :input :sym :ws-in :port-name "input 2" :display-name "WaveStation"}}
             (:inputs pm))))))

(deftest load-output-test
  (testing "Output setup."
    (let [pm (loader/load-pm-file test-file)]
      (is (= {:ws-out {:type :output :sym :ws-out :port-name "output 1" :display-name "WaveStation"}
              :sj {:type :output :sym :sj :port-name "output 2" :display-name "SuperJupiter"}
              :drums {:type :output :sym :drums :port-name "output 2" :display-name "Drums"}}
             (:outputs pm))))))

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
      (is (= 3 (count (:song-lists pm)))) ; All Songs + two more
      (let [sl (second (:song-lists pm))]
        (is (= "Tonight's Song List") (:name sl))
        (is (= 2 (count (:song-indexes sl))))
        (is (= 0 (-> sl :song-indexes first)))
        (is (= 1 (-> sl :song-indexes second)))))))

(deftest create-all-songs-list-test
  (testing "Loading all songs."
    (let [pm (loader/load-pm-file test-file)
          asl (first (:song-lists pm))]
      (is (= "All Songs" (:name asl)))
      (let [n (count (:songs pm))]
        (is (= n (count (:song-indexes asl))))
        (is (= (range n) (:song-indexes asl)))))))
