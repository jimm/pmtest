(ns pmtest.core
  (require [pmtest.portmidi :as pm])
  (:gen-class))

(defn midi-through
  []
  (let [input (pm/open-input-named "MidiPipe Output 1")
        output (pm/open-output-named "SimpleSynth virtual input")]
    (println "Stop with ^C")
    (try
      (loop []
        (pm/wait-for-input input)
        (pm/midi-write output (pm/midi-read input))
        (recur))
      (finally
        (pm/close input)
        (pm/close output)))))

(defn -main
  "Display all MIDI devices, then run `midi-through`."
  []
  (pm/list-devices)
  (midi-through)
  (flush))
