(ns pmtest.midi
  (require [pmtest.portmidi :as pm]))

(defn -open-io-named
  [name type-p open-f]
  (let [num-devices (pm/count-devices)
        idx (first (filter #(and (type-p %)
                                 (= (pm/device-name %) name))
                           (range num-devices)))]
    (open-f idx)))

(defn open-input-named
  "Open the input with the given name and return an input stream."
  [name]
  (-open-io-named name pm/input-device? pm/open-input))

(defn open-output-named
  "Open the output with the given name and return an output stream."
  [name]
  (-open-io-named name pm/output-device? pm/open-output))

(defn -print-devices
  [title pred]
  (println title)
  (let [num-devices (pm/count-devices)]
    (->>
     num-devices
     range
     (filter pred)
     (map #(println (format "  %2d: %s" % (pm/device-name %)))))))

(defn list-devices
  "List all MIDI devices to stdout."
  []
  (let [num-devices (pm/count-devices)]
    (dorun (-print-devices "Inputs" pm/input-device?))
    (dorun (-print-devices "Outputs" pm/output-device?))))
