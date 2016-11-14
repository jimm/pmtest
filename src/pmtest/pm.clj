(ns pmtest.pm
  (:require [pmtest.cursor :as cursor]))

;;; ================ movement ================

(defn next-song
  [pm]
  (assoc pm :cursor (cursor/next-song pm)))

(defn prev-song
  [pm]
  (assoc pm :cursor (cursor/prev-song pm)))

(defn next-patch
  [pm]
  (assoc pm :cursor (cursor/next-patch pm)))

(defn prev-patch
  [pm]
  (assoc pm :cursor (cursor/prev-patch pm)))

;;; ================ messages and triggers ================

(defn send-message
  [pm name]
  (let [messages (get (:messages pm) name)]
    (when messages
      ;; TODO
      )))
