(ns pmtest.connection
  (:use [pmtest.portmidi :as pm]))

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

(defn -midi-in
  [conn message]
  (when (-accept-from-input? conn message)
    ;; TODO
  ))

(defn -midi-out
  [conn message]
  (pm/midi-write (:out conn) message))

(defn start
  ([conn] (start conn nil))
  ([conn start-messages]
   (when start-messages (map #(-midi-out conn %) start-messages))
   (when (:bank-msb conn) (-midi-out conn (pm/message (+ 0xb0 (:out-chan conn)) 32 (:bank-msb conn))))
   (when (:bank-lsb conn) (-midi-out conn (pm/message (+ 0xb0 (:out-chan conn))  0 (:bank-lsb conn))))
   (when (:prog-chg conn) (-midi-out conn (pm/message (+ 0xc0 (:out-chan conn)) (:prog-chg conn))))
   (assoc-in conn [:in :connections]
             (conj conn (get-in conn [:in :connections])))))

(defn stop
  ([conn] (stop conn nil))
  ([conn stop-messages]
   (when stop-messages (map #(-midi-out conn %) stop-messages))
   (assoc-in conn [:in :connections]
             (remove (get-in conn [:in :connections]) #(= conn %)))))
