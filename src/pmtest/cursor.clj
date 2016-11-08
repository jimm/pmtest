;;; A cursor knows the current song list, song, and patch, how to move
;;; between songs and patches, and how to find them given name regexes.
;;; When moving, the old patch is stopped and the new patch is started.

(ns pmtest.cursor
  (:use [pmtest.patch :as patch]))

(defn make-cursor
  [pm]
  (let [song-list (first (:song-lists pm))
        song (first (:songs song-list))
        patch (first (:patches song))]
    {:pm pm
     :song-list song-list
     :song-list-index (if song-list 0 nil)
     :song song
     :song-index (if song 0 nil)
     :patch patch
     :patch-index (if patch 0 nil)}))

(defn -move-song
  [c bounds-test index-mod]
  [c]
  (let [song-list (:song-list c)
        song-index (:song-index c)]
    (if (bounds-test song-index song-list)
      (let [new-song-index (index-mod song-index)
            song (nth song-list new-song-index)
            patch (first (:patches song))]
        (patch/stop (:patch c))
        (patch/start patch)
        (assoc c
               :song song
               :song-index new-song-index
               :patch patch
               :patch-index (if patch 0 nil)))
      c)))

(defn next-song
  "Go to first patch of next song. Do nothing if this is the last song in
  the song list."
  [c]
  (-move-song #(< %1 %2) #(inc %)))

(defn prev-song
  "Go to first patch of previous song. Do nothing if this is the first song
  in the song list."
  [c]
  (-move-song #(> %1 0) #(dec %)))

(defn -move-patch
  [c in-bounds-func if-out-of-bounds index-func]
  (if (in-bounds-func (:patch-index c))
    (let [patch-index (index-func (:patch-index c))
          patch (nth (get-in c [:song :patches]) patch-index)]
      (patch/stop (:patch c))
      (patch/start patch)
      (assoc c
             :patch patch
             :patch-index patch-index))
    (if-out-of-bounds c)))

(defn next-patch
  "Go to the next patch. If this is the last patch of the song, go to the
  next song in the song list."
  [c]
  (-move-patch
   #(let [num-patches (count (get-in c [:song :patches]))]
      (< % (dec num-patches)))
   next-song
   inc))

(defn prev-patch
  "Go to the previous patch. If this is the first patch of the song, go to
  the previous song in the song list."
  [c]
  (-move-patch (complement zero?) prev-song dec))
