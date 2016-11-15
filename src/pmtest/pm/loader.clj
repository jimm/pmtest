(ns pmtest.pm.loader
  (:require [pmtest.cursor :as cursor]
            [pmtest.io :as io]))

(defn make-pm
  ([] (make-pm {}))
  ([m]
   (let [pm (merge {:inputs []
                    :outputs []
                    :song-lists []
                    :songs []
                    :cursor nil
                    :messages {}                ; map of name => seq of messages
                    :triggers {}}
                   m)]
     (assoc pm :cursor (cursor/make-cursor pm)))))

(defn -make-conn
  [c inputs outputs]
  (assoc c
;; TODO
         :in (:in c)))

(defn -make-patch
  [p inputs outputs]
  (assoc p :connections (map #(-make-conn % inputs outputs) (:connections p))))

(defn -make-song
  [s inputs outputs]
  (assoc s :patches (map #(-make-patch % inputs outputs) (:patches s))))

(defn -make-songs
  [pm-struct inputs outputs]
  (map #(-make-song % inputs outputs) (:songs pm-struct)))

(defn -make-song-list
  "Replace song names with songs in song-list."
  [song-list songs]
  (let [songs-from-names (for [n (:songs song-list)]
                           (first (filter #(= n (:name %)) songs)))]
    (assoc song-list :songs songs-from-names)))

(defn -make-song-lists
  [pm-struct]
  (let [songs (:songs pm-struct)]
    (map #(-make-song-list % songs) (:song-lists pm-struct))))

(defn -portmidi-ios
  [vecs type]
  (let [ios (for [[port-name sym-map] vecs
                  [io-sym io-display-name] sym-map]
              (io/make-io type io-sym port-name io-display-name))]
    (zipmap (map :sym ios) ios)))

(defn -portmidi-inputs
  "Input is a seq of seqs, each consisting of a PortMidi input device name
  and a map of symbols to display names. Output is a map of symbols to
  pmtest.io maps.

  Input:
  [{\"name\" {:sym1 \"Pretty Name\" :sym2 \"Another\"}]

  Output:
  {:sym {:type :input :sym :sym :port-name \"name\" :display-name \"Pretty Name\"}
   :sym2 {:type :input :sym :sym2 :port-name \"name\" :display-name \"Another\"}}"
  [in-vecs]
  (-portmidi-ios in-vecs :input))

(defn -portmidi-outputs
  "Input is a seq of seqs, each consisting of a PortMidi output device name
  and a map of symbols to display names. Output is a map of symbols to
  pmtest.io maps.

  Input:
  [{\"name\" {:sym1 \"Pretty Name\" :sym2 \"Another\"}]

  Output:
  {:sym {:type :output :sym :sym :port-name \"name\" :display-name \"Pretty Name\"}
   :sym2 {:type :output :sym :sym2 :port-name \"name\" :display-name \"Another\"}}"
  [out-vecs]
  (-portmidi-ios out-vecs :output))

(defn load-pm-file
  [file]
  (let [pm-struct (load-string (slurp file))
        inputs (-portmidi-inputs (:inputs pm-struct))
        outputs (-portmidi-outputs (:outputs pm-struct))
        songs (-make-songs pm-struct inputs outputs)
        pm (assoc (make-pm pm-struct)
                  :inputs inputs
                  :outputs outputs
                  :songs songs
                  :song-lists (-make-song-lists pm-struct))]
    (assoc pm :cursor (cursor/make-cursor pm))))
