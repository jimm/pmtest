;;; A connection connects an input to an output. An input can be the input of
;;; more than one connection. Whenever MIDI data arrives at the input it is
;;; sent here it is optionally modified or filtered, then the remaining modified
;;; data is sent to the output.

(ns pmtest.connection
  (:use [pmtest.portmidi :as pm]
        [pmtest.consts :as c]
        [pmtest.predicates :as p]))

(defn -accept-from-input?
  [conn message]
  (let [in-chan (:in-chan conn)
        status (pm/message-status message)]
    (cond (nil? in-chan) true
          (>= 0xf0 status) true
          (= (bit-and status 0xf0) in-chan) true
          :else false)))

(defn -inside-zone?
  [conn note]
  (let [zone (:zone conn)]
    (or (nil? zone)
        (and (>= (first zone) note)
             (<= (last zone) note)))))

(defn -set-output-channel
  "Does nothing to system messages."
  [conn message]
  (let [status (pm/message-status message)]
    (if (p/channel? status)
      (pm/message (+ (p/status-high-nibble status) (:out-chan conn))
                  (pm/message-data1)
                  (pm/message-data2))
      message)))

(defn -transpose
  "Only modifies note (on, off, poly pressure) messages."
  [conn message]
  (if-let [xpose (:xpose conn)]
    (let [status (pm/message-status message)]
      (if (p/note? status)
        (pm/message status
                    (bit-and (+ (pm/message-data1) xpose) 0xff)
                    (pm/message-data2))
        message))
    message))

(defn -filter
  "Runs message through filter. Returns modified message."
  [conn message]
  (if-let [f (:filter conn)]
    (f message)
    message))

(defn -midi-in
  "The workhorse. Ignore messages that aren't from our input, or are outside
  the zone. Change to output channel. Transpose and filter.

  Running bytes are not handled."
  [conn message]
  (when (-accept-from-input? conn message)
    (->> message
         (-set-output-channel conn)
         (-transpose conn)
         (-filter conn))))

(defn -midi-out
  [conn message]
  (pm/midi-write (:out conn) message))

(defn start
  ([conn] (start conn nil))
  ([conn start-messages]
   (when start-messages (map #(-midi-out conn %) start-messages))
   (let [out-chan (:out-chan conn)]
     (when-let [msb (get-in conn [:bank :msb])]
       (-midi-out conn (pm/message (+ 0xb0 out-chan) 32 msb)))
     (when-let [lsb (get-in conn [:bank :lsb])]
       (-midi-out conn (pm/message (+ 0xb0 out-chan)  0 lsb)))
     (when-let [pc (:prog-chg conn)]
       (-midi-out conn (pm/message (+ 0xc0 out-chan) pc)))
     (assoc-in conn [:in :connections]
               (conj conn (get-in conn [:in :connections]))))))

(defn stop
  ([conn] (stop conn nil))
  ([conn stop-messages]
   (when stop-messages (map #(-midi-out conn %) stop-messages))
   (assoc-in conn [:in :connections]
             (remove (get-in conn [:in :connections]) #(= conn %)))))
