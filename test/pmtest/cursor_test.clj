(ns pmtest.cursor-test
  (:require [clojure.test :refer :all]
            [pmtest.pm.loader :as loader :only :load-pm-file]
            [pmtest.cursor :as csr :refer :all]))

(def test-file "examples/test.clj")
(def test-pm (loader/load-pm-file test-file))
(def test-cursor (csr/make-cursor test-pm))

(defmacro cursor-check
  [c song-list-index song-index patch-index]
  `(do
     (is (= ~song-list-index (:song-list-index ~c)) "Song list index mismatch")
     (is (= ~song-index (:song-index ~c)) "Song index mismatch")
     (is (= ~patch-index (:patch-index ~c)) "Patch index mismatch")))

(defn npatch [c] (csr/next-patch c test-pm))
(defn ppatch [c] (csr/prev-patch c test-pm))
(defn nsong  [c] (csr/next-song  c test-pm))
(defn psong  [c] (csr/prev-song  c test-pm))

(deftest load-initialization
  (testing "Input setup."
    (cursor-check test-cursor 0 0 0)))

(deftest test-next-patch
  (testing "Next patch."
    (let [c (npatch test-cursor)]
      (cursor-check c 0 0 1))))

(deftest test-next-patch-at-end-of-song
  (testing "Next patch at end of song."
    (let [c (npatch (npatch test-cursor))]
      (cursor-check c 0 1 0))))

(deftest test-next-patch-at-end-of-song-list
  (testing "next-patch-at-end-of-song-list"
    (let [c (-> test-cursor nsong nsong npatch)]
      (cursor-check c 0 2 0))))

(deftest test-prev-patch
  (testing "prev-patch"
    (let [c (-> test-cursor npatch ppatch)]
      (cursor-check c 0 0 0))))

(deftest test-prev-patch-start-of-song
  (testing "prev-patch-start-of-song"
    (let [c (-> test-cursor nsong ppatch)]
      (cursor-check c 0 0 0))))

(deftest test-prev-patch-start-of-song-list
  (testing "prev-patch-start-of-song-list"
    (let [c (-> test-cursor nsong ppatch)]
      (cursor-check c 0 0 0))))

(deftest test-next-song
  (testing "next-song"
    (let [c (-> test-cursor nsong)]
      (cursor-check c 0 1 0))))

(deftest test-prev-song
  (testing "prev-song"
    (let [c (-> test-cursor nsong npatch psong)]
      (cursor-check c 0 0 0))))

(deftest test-song-list
  (testing "song-list"
    (is (= "All Songs" (-> test-cursor (song-list test-pm) :name)))))

(deftest test-song
  (testing "song"
    (is (= "First Song" (-> test-cursor (song test-pm) :name)))))

(deftest test-patch
  (testing "patch"
    (is (= "First Song First Patch" (-> test-cursor (patch test-pm) :name)))))

#_(deftest test-goto-song
  (testing "goto-song"
    ;; TODO
    ))

#_(deftest test-goto-song-no-such-song
  (testing "goto-song-no-such-song"
    ;; TODO
    ))

#_(deftest test-goto-song-list
  (testing "goto-song-list"
    ;; TODO
    ))

#_(deftest test-goto-song-list-no-such-song-list
  (testing "goto-song-list-no-such-song-list"
    ;; TODO
    ))

#_(deftest test-attempt-goto
  (testing "attempt-goto"
    ;; TODO
    ))
