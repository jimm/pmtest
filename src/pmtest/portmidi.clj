;;; A wrapper around the JPortMidiApi class.

(ns pmtest.portmidi
  (use [pmtest.consts :as c])
  (import [jportmidi JPortMidiApi JPortMidiException
           JPortMidiApi$PortMidiStream JPortMidiApi$PmEvent]))

(def ^:dynamic *bufsize* 1024)
(def ^:dynamic *latency* 0)

;;; **************** system ****************

(defn initialize [] (jportmidi.JPortMidiApi/Pm_Initialize))

(defn terminate [] (jportmidi.JPortMidiApi/Pm_Terminate))

(defn refresh-device-lists
  "WARNING: do not call if any devices are open."
  []
  (terminate)
  (initialize))

(defn host-error? [stream] (jportmidi.JPortMidiApi/Pm_HasHostError stream))

(defn error-text [errnum] (jportmidi.JPortMidiApi/Pm_GetErrorText errnum))

(defn host-error-text [] (jportmidi.JPortMidiApi/Pm_GetHostErrorText))

(defn check-error
  [err]
  (cond (>= err 0) err
        (= err -10000) (throw (JPortMidiException. err (host-error-text)))
        :else (throw (JPortMidiException. err (error-text err)))))

;;; **************** query device info ****************

(defn count-devices [] (jportmidi.JPortMidiApi/Pm_CountDevices))

(defn default-input-device-id [] (jportmidi.JPortMidiApi/Pm_GetDefaultInputDeviceID))

(defn default-output-device-id [] (jportmidi.JPortMidiApi/Pm_GetDefaultOutputDeviceID))

(defn device-interface [i] (jportmidi.JPortMidiApi/Pm_GetDeviceInterf i))

(defn device-name [i] (jportmidi.JPortMidiApi/Pm_GetDeviceName i))

(defn input-device? [i] (jportmidi.JPortMidiApi/Pm_GetDeviceInput i))

(defn output-device? [i] (jportmidi.JPortMidiApi/Pm_GetDeviceOutput i))

(defn -print-devices
  [title pred]
  (println title)
  (let [num-devices (count-devices)]
    (->>
     num-devices
     range
     (filter pred)
     (map #(println (format "  %2d: %s" % (device-name %)))))))

(defn list-devices
  "List all MIDI devices to stdout."
  []
  (let [num-devices (count-devices)]
    (dorun (-print-devices "Inputs" input-device?))
    (dorun (-print-devices "Outputs" output-device?))))

;;; **************** open devices ****************

(defn open-input
  [input-device]
  (let [stream (jportmidi.JPortMidiApi$PortMidiStream.)]
    (check-error (jportmidi.JPortMidiApi/Pm_OpenInput stream input-device nil *bufsize*))
    stream))

(defn open-output
  [output-device]
  (let [stream (jportmidi.JPortMidiApi$PortMidiStream.)]
    (check-error (jportmidi.JPortMidiApi/Pm_OpenOutput stream output-device nil *bufsize*
                                                       *latency*))
    stream))

(defn -open-io-named
  [name type-p open-f]
  (let [num-devices (count-devices)
        idx (first (filter #(and (type-p %)
                                 (= (device-name %) name))
                           (range num-devices)))]
    (open-f idx)))

(defn open-input-named
  "Open the input with the given name and return an input stream."
  [name]
  (-open-io-named name input-device? open-input))

(defn open-output-named
  "Open the output with the given name and return an output stream."
  [name]
  (-open-io-named name output-device? open-output))

;;; **************** messages and structs ****************

(defn set-filter
  [stream filters]
  (jportmidi.JPortMidiApi/Pm_SetFilter stream filters))

(defn set-channel-mask
  "Mask is a MIDI channel bitmask. Channel 0 is the low byte."
  [stream mask]
  (jportmidi.JPortMidiApi/Pm_SetChannelMask stream mask))

(defn abort [stream] (jportmidi.JPortMidiApi/Pm_Abort stream))

(defn close [stream] (jportmidi.JPortMidiApi/Pm_Close stream))

(defn message
  ([status] (message status 0 0))
  ([status data1] (message status data1 0))
  ([status data1 data2]
   (+ (bit-shift-left (bit-and data2 0xff) 16)
      (bit-shift-left (bit-and data1 0xff)  8)
      (bit-and status 0xff))))

(defn message-status [msg] (bit-and msg 0xff))
(defn message-channel-status [msg] (bit-and msg 0xf0))
(defn message-channel [msg] (bit-and msg 0x0f))
(defn message-data1 [msg] (bit-and (bit-shift-right msg 8) 0xff))
(defn message-data2 [msg] (bit-and (bit-shift-right msg 16) 0xff))

(defn make-pm-event [] (jportmidi.JPortMidiApi$PmEvent.))
(defn pm-event-message [pm-event] (.message pm-event))
(defn pm-event-timestamp [pm-event] (.timestamp pm-event))

;;; **************** reading and writing ****************

;; Avoid conflict with clojure.core/read method name.
(defn midi-read
  [stream]
  (let [buffer (make-pm-event)]
    (check-error (jportmidi.JPortMidiApi/Pm_Read stream buffer))
    buffer))

(defn poll [stream] (jportmidi.JPortMidiApi/Pm_Poll stream))

(defn wait-for-input
  [stream]
  (loop [ready (poll stream)]
    (when-not ready
      (Thread/sleep 10)
      (recur (poll stream)))))

;; Avoid conflict with clojure.core/write method name.
(defn midi-write [stream buffer] (jportmidi.JPortMidiApi/Pm_Write stream buffer))

(defn midi-write-short
  ([stream msg] (midi-write-short stream 0 msg))
  ([stream when msg]
   (jportmidi.JPortMidiApi/Pm_WriteShort stream when msg)))

(defn midi-write-sysex
  ([stream bytes] (midi-write-sysex stream 0 bytes))
  ([stream when bytes]
   (jportmidi.JPortMidiApi/Pm_WriteSysEx stream when bytes)))

;;; **************** time ****************

(defn time-start
  [resolution]
  (jportmidi.JPortMidiApi/Pt_TimeStart resolution))

(defn time-stop
  []
  (jportmidi.JPortMidiApi/Pt_TimeStop))

;; Avoid conflict with clojure.core/time method name.
(defn time-get
  []
  (jportmidi.JPortMidiApi/Pt_Time))

(defn time-started?
  []
  (jportmidi.JPortMidiApi/Pt_TimeStarted))
