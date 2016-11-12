;;; All of these predicate messages can take either a message or a status
;;; byte. Behavior is undefined if you pass a non-status byte.

(ns pmtest.predicates
  (:use [pmtest.portmidi :as pm :only (message-status)]
        [pmtest.consts :as c]))

;;; ================ accessors ================

(defn status
  "Returns first byte if it's a message, whole byte if it's a byte."
  [m]
  (if (< m 256) m (pm/message-status m)))

(defn status-high-nibble [m] (bit-and (status m) 0xf0))

(defn channel [m] (bit-and (status m) 0x0f))

;;; ================ predicates ================

(defn channel? [m] (< (status m) c/sysex))

(defn note-on? [m] (= c/note-on (status-high-nibble m)))

(defn note-off? [m] (= c/note-off (status-high-nibble m)))

(defn poly-pressure? [m] (= c/poly-pressure (status-high-nibble m)))

(defn note? [m] (< (status m) c/controller))

(defn controller? [m] (= c/controller (status-high-nibble m)))

(defn program-change? [m] (= c/program-change (status-high-nibble m)))

(defn pitch-bend? [m] (= c/pitch-bend (status-high-nibble m)))

(defn system? [m] (<= c/sysex (status m) c/eox))

(defn realtime? [m] (<= c/clock (status m) c/system-reset))
