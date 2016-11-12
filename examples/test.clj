(require '[pmtest.consts :as c]
         'pmtest.pm)

{:inputs [["input 1" :mb "midiboard"]
          ["input 2" :ws-in "WaveStation"]]
 :outputs [["output 1" :ws-out "WaveStation"]
           ;; In this setup output 2 => SuperJupiter => MIDI thru => Drum
           ;; machine.
           ["output 2" :sj "SuperJupiter"]
           ["output 2" :drums]] ;; default name "output 2"
 :messages [
            ["Tune Request" [(pmtest.portmidi/message c/tune-request)]]
            ["Full Volume" (map #(pmtest.portmidi/message (+ c/controller %) c/cc-volume 0xff) (range 16))]
            ]
 :message-keys {:f1 "Tune Request"
                :f2 "Full Volume"}
 :triggers [
            ;; All on MIDI channel 1
            [:mb (pmtest.portmidi/message c/controller c/cc-gen-purpose-1 0xff) #(pmtest.pm/next-patch %)]
            [:mb (pmtest.portmidi/message c/controller c/cc-gen-purpose-2 0xff) #(pmtest.pm/prev-patch %)]
            [:mb (pmtest.portmidi/message c/controller c/cc-gen-purpose-3 0xff) #(pmtest.pm/next-song %)]
            [:mb (pmtest.portmidi/message c/controller c/cc-gen-purpose-4 0xff) #(pmtest.pm/prev-song %)]
            [:ws-in (pmtest.portmidi/message c/tune-request)
             #(do (pmtest.pm/send-message % "Tune Request")
                  (pmtest.pm/send-message % "Full Volume"))]
            ]
 :songs [
         {:name "First Song"
          :notes """
                 Notes about this song
                 can span multiple lines.
                 """
          :patches [
                    {:name "First Song First Patch"
                     :start-messages [(pmtest.portmidi/message c/tune-request)]
                     :connections [
                                   {:in :mb
                                    :out :sj
                                    :out-chan 2
                                    :prog-chg 64
                                    :zone (range 64 76)
                                    :xpose 12}
                                   {:in :mb
                                    :in-chan 10
                                    :out :drums
                                    :out-chan 10
                                    :bank {:msb 1 :lsb 23}
                                    :prog-chg 2
                                    :zone (range 64 76)
                                    :xpose 12}
                                   {:in :ws-in
                                    :out :ws-out
                                    :out-chan 4
                                    :bank {:msb 2}
                                    :program 100
                                    :filter (fn [conn msg] if (pmtest.portmidi/note-on? msg)
                                               (pmtest.portmidi/message (pmtest.portmidi/message-status msg)
                                                           (pmtest.portmidi/message-data1 msg)
                                                           (max 0 (dec (pmtest.portmidi/message-data2 msg))))
                                               msg)}
                                   ]}
                    {:name "First Song Second Patch"}]}

         {:name "Second Song"
          :patches [
                    {:name "Second Song First Patch"
                     :stop [(pmtest.portmidi/message c/tune-request)]
                     :conns [
                             {:io {:mb nil :sj 4} :prog-chg 22 :zone (range 76 128)}
                             {:io {:ws-in nil :ws-out :6} :zone (range 64 76)
                              :filter second} ;; no-op; filter is optional, this is just an example
                             ]}
                    {:name "Second Song Second Patch"}]}
         {:name "Third Song"
          :patches [{:name "Third Song First Patch"}]}
         ]
 :song-lists [
              {:name "Tonight's Song List"
               :songs [
                       "First Song"
                       "Second Song"
                       ]}
              {:name "Another Song List"
               :songs [
                       "Third Song"
                       "Second Song"
                       ]}]}
