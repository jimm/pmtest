(ns pmtest.core
  (require [pmtest.midi :as midi]
           [pmtest.portmidi :as pm])
  (:gen-class))

(defn midi-through
  []
  (let [input (midi/open-input-named "MidiPipe Output 1")
        output (midi/open-output-named "SimpleSynth virtual input")]
    (loop [_ (pm/poll input)]
      (pm/midi-write output (pm/midi-read input))
      (recur (pm/poll)))))

(defn -main
  "Display all MIDI devices"
  []
  (midi/list-devices)
  (flush))
