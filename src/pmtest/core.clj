(ns pmtest.core
  (import [jportmidi.JPortMidi])
  (:gen-class))

(defn print-devices
  [jpm title pred]
  (println title)
  (let [num-devices (.countDevices jpm)]
    (->>
     num-devices
     (range 0)
     (filter pred)
     (map #(println "  " (.getDeviceName jpm %))))))

(defn -main
  "Display all MIDI devices"
  []
  (let [jpm (jportmidi.JPortMidi.)
        num-devices (.countDevices jpm)]
    (println "There are" num-devices "devices attached")
    (dorun
     (print-devices jpm "Inputs" #(.getDeviceInput jpm %)))
    (dorun
     (print-devices jpm "Outputs" #(.getDeviceOutput jpm %))))
  (flush))

