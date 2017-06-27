;;; A cursor knows the current song list, song, and patch, how to move
;;; between songs and patches, and how to find them given name regexes.
;;; When moving, the old patch is stopped and the new patch is started.

(ns pmtest.cursor
  (:use [pmtest.patch :as patch]))

(defn make-cursor
  [pm]
  (let [song-index (if (-> pm :songs count pos?) 0 nil)]
    {:song-list-index 0
     :song-index song-index
     :patch-index (if (and song-index
                           (pos? (-> pm :songs first :patches count)))
                    0 nil)}))

(defn -all-songs-list [pm] (first (:song-lists pm)))

(defn song-list
  [c pm]
  (when-let [i (:song-list-index c)]
    (nth (:song-lists pm) i)))

(defn song
  ([c pm] (song c pm (:song-index c)))
  ([c pm idx]
  (when-let [sl (song-list c pm)]
    (when idx
      (nth (:songs pm)
           (nth (:song-indexes sl) idx))))))

(defn patch
  [c pm]
  (when-let [s (song c pm)]
    (when-let [i (:patch-index c)]
      (nth (:patches s) i))))

(defn -move-song
  [c pm bounds-test index-mod]
  (let [sl (song-list c pm)
        song-index (:song-index c)]
    (if (bounds-test song-index (count (:song-indexes sl)))
      (let [new-song-index (index-mod song-index)
            new-song (song c pm new-song-index)
            patch (first (:patches new-song))]
        (patch/stop (:patch c))
        (patch/start patch)
        (assoc c
               :song-index new-song-index
               :patch-index (if patch 0 nil)))
      c)))

(defn next-song
  "Go to first patch of next song. Do nothing if this is the last song in
  the song list."
  [c pm]
  (-move-song c pm #(< %1 (dec %2)) inc))

(defn prev-song
  "Go to first patch of previous song. Do nothing if this is the first song
  in the song list."
  [c pm]
  (-move-song c pm (fn [i _] > i 0) dec))

(defn -move-patch
  [c pm in-bounds-func if-out-of-bounds index-func]
  (if (in-bounds-func (:patch-index c))
    (let [patch-index (index-func (:patch-index c))
          patch (nth (:patches (song c pm)) patch-index)]
      (patch/stop (:patch c))
      (patch/start patch)
      (assoc c
             :patch patch
             :patch-index patch-index))
    (if-out-of-bounds c)))

(defn next-patch
  "Go to the next patch. If this is the last patch of the song, go to the
  next song in the song list."
  [c pm]
  (-move-patch
   c
   pm
   #(let [s (song c pm)]
      (if s
        (let [num-patches (count (:patches s))]
          (< % (dec num-patches)))
        false))
   #(next-song % pm)
   inc))

(defn prev-patch
  "Go to the previous patch. If this is the first patch of the song, go to
  the previous song in the song list."
  [c pm]
  (-move-patch c pm (complement zero?) #(prev-song % pm) dec))

(defn goto-song
  [c pm name-regex]
  ;; use (re-find (re-pattern (str "(?i)" name-regex)) (:name thing))
  (let [sl (or (song-list c pm) (-all-songs-list))]
    (nth (:songs pm)
         (first (filter #(re-find (re-pattern (str "(?i)" name-regex)) (:name %))
                        (:songs sl))))))

(defn goto-song-list
  [c pm name-regex]
  ;; TODO
  )

(defn mark
  "Remembers the names of the current song list, song, and patch.
  Used by restore."
  [c pm]
  ;; TODO
  )

(defn restore
  "Using the names saved by #save, try to find them now.

  Since names can change we use Damerau-Levenshtein distance on lowercase
  versions of all strings."
  [c pm]
  ;; TODO
  )

(defn find-nearest-match
  "List must contain objects that respond to #name. If str is nil or
  xs is nil or empty then nil is returned."
  [c xs str]
  ;; TODO
  )

(defn damerau-levenshtein
  "https://gist.github.com/182759 (git://gist.github.com/182759.git)
  Referenced from http://en.wikipedia.org/wiki/Damerau%E2%80%93Levenshtein_distance"
  [s1 s2]
  ;; TODO
  )
