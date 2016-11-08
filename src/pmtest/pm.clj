(ns pmtest.pm
  (:require [pmtest.cursor :as cursor]))

;;; ================ creation ================

(defn make-pm
  []
  (let [pm {:song-lists []
            :songs []
            :cursor nil
            :messages {}                ; map of name => seq of messages
            :triggers {}}]
    (assoc pm :cursor (cursor/make-cursor pm))))

(defn -make-conn
  [c inputs outputs]
  (assoc c
         :in 

(defn -make-patch
  [p inputs outputs]
  (assoc p :connections (map #(-make-conn % inputs outputs) (:connections p))))

(defn -make-song
  [s inputs outputs]
  (assoc s :patches (map #(-make-patch % inputs outputs) (:patches s))))

(defn -make-songs
  [pm-struct inputs outputs]
  (map #(-make-song % inputs outputs) (:songs pm-struct)))

(defn -portmidi-io
  [v]
  ;; TODO
  {})

(defn -portmidi-inputs
  [in-vecs]
  ;; TODO new IO type
  ;; FIXME
  (map identity in-vecs))

(defn -portmidi-outputs
  [in-vecs]
  ;; TODO new IO type
  ;; FIXME
  (map identity in-vecs))

(defn read-pm-file
  [file]
  (let [pm-struct (load-string (slurp file))
        inputs (-portmidi-inputs (:inputs pm-struct))
        outputs (-portmidi-outputs (:outputs pm-struct))
        songs (-make-songs pm-struct inputs outputs)
        pm (assoc (make-pm) :songs songs)]
    (assoc pm :cursor (cursor/make-cursor pm))))

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
