(ns pmtest.consts)

;; Number of MIDI channels
(def midi-channels 16)

;; Number of note per MIDI channel
(def notes-per-channel 128)

;; Standard MIDI File meta event defs.
(def meta-event 0xff)
(def meta-seq-num 0x00)
(def meta-text 0x01)
(def meta-copyright 0x02)
(def meta-seq-name 0x03)
(def meta-instrument 0x04)
(def meta-lyric 0x05)
(def meta-marker 0x06)
(def meta-cue 0x07)
(def meta-midi-chan-prefix 0x20)
(def meta-track-end 0x2f)
(def meta-set-tempo 0x51)
(def meta-smpte 0x54)
(def meta-time-sig 0x58)
(def meta-patch-sig 0x59)
(def meta-seq-specif 0x7f)

;; Channel messages

;; Note, val
(def note-off 0x80)
;; Note, val
(def note-on 0x90)
;; Note, val
(def poly-pressure 0xA0)
;; Controller ;;, val
(def controller 0xB0)
;; Program number
(def program-change 0xC0)
;; Channel pressure
(def channel-pressure 0xD0)
;; LSB, MSB
(def pitch-bend 0xE0)

;; System common messages

;; System exclusive start
(def sysex 0xF0)
;; Beats from top: LSB/MSB 6 ticks = 1 beat
(def song-pointer 0xF2)
;; Val = number of song
(def song-select 0xF3)
;; Tune request
(def tune-request 0xF6)
;; End of system exclusive
(def eox 0xF7)

;; System realtime messages

;; MIDI clock (24 per quarter note)
(def clock 0xF8)
;; Sequence start
(def seq-start 0xFA)
;; Sequence continue
(def seq-continue 0xFB)
;; Sequence stop
(def seq-stop 0xFC)
;; Active sensing (sent every 300 ms when nothing else being sent)
(def active-sense 0xFE)
;; System reset
(def system-reset 0xFF)

;; Controller numbers

;; = 0 - 31 = continuous, MSB
;; = 32 - 63 = continuous, LSB
;; = 64 - 97 = momentary switches
(def cc-bank-select 0)
(def cc-bank-select-msb 0)
(def cc-mod-wheel 1)
(def cc-mod-wheel-msb 1)
(def cc-breath-controller 2)
(def cc-breath-controller-msb 2)
(def cc-foot-controller 4)
(def cc-foot-controller-msb 4)
(def cc-portamento-time 5)
(def cc-portamento-time-msb 5)
(def cc-data-entry 6)
(def cc-data-entry-msb 6)
(def cc-volume 7)
(def cc-volume-msb 7)
(def cc-balance 8)
(def cc-balance-msb 8)
(def cc-pan 10)
(def cc-pan-msb 10)
(def cc-expression-controller 11)
(def cc-expression-controller-msb 11)
(def cc-gen-purpose-1 16)
(def cc-gen-purpose-1-msb 16)
(def cc-gen-purpose-2 17)
(def cc-gen-purpose-2-msb 17)
(def cc-gen-purpose-3 18)
(def cc-gen-purpose-3-msb 18)
(def cc-gen-purpose-4 19)
(def cc-gen-purpose-4-msb 19)

;; [32 - 63] are LSB for [0 - 31]
(def cc-bank-select-lsb 32)
(def cc-bank-select-msb 32)
(def cc-mod-wheel-lsb 32)
(def cc-mod-wheel-msb 32)
(def cc-breath-controller-lsb 32)
(def cc-breath-controller-msb 32)
(def cc-foot-controller-lsb 32)
(def cc-foot-controller-msb 32)
(def cc-portamento-time-lsb 32)
(def cc-portamento-time-msb 32)
(def cc-data-entry-lsb 32)
(def cc-data-entry-msb 32)
(def cc-volume-lsb 32)
(def cc-volume-msb 32)
(def cc-balance-lsb 32)
(def cc-balance-msb 32)
(def cc-pan-lsb 32)
(def cc-pan-msb 32)
(def cc-expression-controller-lsb 32)
(def cc-expression-controller-msb 32)
(def cc-gen-purpose-1-lsb 32)
(def cc-gen-purpose-1-msb 32)
(def cc-gen-purpose-2-lsb 32)
(def cc-gen-purpose-2-msb 32)
(def cc-gen-purpose-3-lsb 32)
(def cc-gen-purpose-3-msb 32)
(def cc-gen-purpose-4-lsb 32)
(def cc-gen-purpose-4-msb 32)

;; Momentary switches:
(def cc-sustain 64)
(def cc-portamento 65)
(def cc-sustenuto 66)
(def cc-soft-pedal 67)
(def cc-hold-2 69)
(def cc-gen-purpose-5 50)
(def cc-gen-purpose-6 51)
(def cc-gen-purpose-7 52)
(def cc-gen-purpose-8 53)
(def cc-ext-effects-depth 91)
(def cc-tremelo-depth 92)
(def cc-chorus-depth 93)
(def cc-detune-depth 94)
(def cc-phaser-depth 95)
(def cc-data-increment 96)
(def cc-data-decrement 97)
(def cc-nreg-param-lsb 98)
(def cc-nreg-param-msb 99)
(def cc-reg-param-lsb 100)
(def cc-reg-param-msb 101)

;; Channel mode message values

;; Val 0 == off, 0x7f == on
(def cm-reset-all-controllers 0x79)
(def cm-local-control 0x7A)
(def cm-all-notes-off 0x7B) ;; Val must be 0
(def cm-omni-mode-off 0x7C) ;; Val must be 0
(def cm-omni-mode-on 0x7D)  ;; Val must be 0
(def cm-mono-mode-on 0x7E)  ;; Val = # chans
(def cm-poly-mode-on 0x7F)  ;; Val must be 0

(def controller-names [
  "Bank Select (MSB)",
  "Modulation (MSB)",
  "Breath Control (MSB)",
  "3 (MSB)",
  "Foot Controller (MSB)",
  "Portamento Time (MSB)",
  "Data Entry (MSB)",
  "Volume (MSB)",
  "Balance (MSB)",
  "9 (MSB)",
  "Pan (MSB)",
  "Expression Control (MSB)",
  "12 (MSB)", "13 (MSB)", "14 (MSB)", "15 (MSB)",
  "General Controller 1 (MSB)",
  "General Controller 2 (MSB)",
  "General Controller 3 (MSB)",
  "General Controller 4 (MSB)",
  "20 (MSB)", "21 (MSB)", "22 (MSB)", "23 (MSB)", "24 (MSB)", "25 (MSB)",
  "26 (MSB)", "27 (MSB)", "28 (MSB)", "29 (MSB)", "30 (MSB)", "31 (MSB)",

  "Bank Select (LSB)",
  "Modulation (LSB)",
  "Breath Control (LSB)",
  "35 (LSB)",
  "Foot Controller (LSB)",
  "Portamento Time (LSB)",
  "Data Entry (LSB)",
  "Volume (LSB)",
  "Balance (LSB)",
  "41 (LSB)",
  "Pan (LSB)",
  "Expression Control (LSB)",
  "44 (LSB)", "45 (LSB)", "46 (LSB)", "47 (LSB)",
  "General Controller 1 (LSB)",
  "General Controller 2 (LSB)",
  "General Controller 3 (LSB)",
  "General Controller 4 (LSB)",
  "52 (LSB)", "53 (LSB)", "54 (LSB)", "55 (LSB)", "56 (LSB)", "57 (LSB)",
  "58 (LSB)", "59 (LSB)", "60 (LSB)", "61 (LSB)", "62 (LSB)", "63 (LSB)",

  "Sustain Pedal",
  "Portamento",
  "Sostenuto",
  "Soft Pedal",
  "68",
  "Hold 2",
  "70", "71", "72", "73", "74", "75", "76", "77", "78", "79",
  "General Controller 5",
  "Tempo Change",
  "General Controller 7",
  "General Controller 8",
  "84", "85", "86", "87", "88", "89", "90",
  "External Effects Depth",
  "Tremolo Depth",
  "Chorus Depth",
  "Detune (Celeste) Depth",
  "Phaser Depth",
  "Data Increment",
  "Data Decrement",
  "Non-Registered Param LSB",
  "Non-Registered Param MSB",
  "Registered Param LSB",
  "Registered Param MSB",
  "102", "103", "104", "105", "106", "107", "108", "109",
  "110", "111", "112", "113", "114", "115", "116", "117",
  "118", "119", "120",
  "Reset All Controllers",
  "Local Control",
  "All Notes Off",
  "Omni Mode Off",
  "Omni Mode On",
  "Mono Mode On",
  "Poly Mode On"
])

;; General MIDI patch names
(def gm-patch-names [
  ;; Pianos
  "Acoustic Grand Piano",
  "Bright Acoustic Piano",
  "Electric Grand Piano",
  "Honky-tonk Piano",
  "Electric Piano 1",
  "Electric Piano 2",
  "Harpsichord",
  "Clavichord",
  ;; Tuned Idiophones
  "Celesta",
  "Glockenspiel",
  "Music Box",
  "Vibraphone",
  "Marimba",
  "Xylophone",
  "Tubular Bells",
  "Dulcimer",
  ;; Organs
  "Drawbar Organ",
  "Percussive Organ",
  "Rock Organ",
  "Church Organ",
  "Reed Organ",
  "Accordion",
  "Harmonica",
  "Tango Accordion",
  ;; Guitars
  "Acoustic Guitar (nylon)",
  "Acoustic Guitar (steel)",
  "Electric Guitar (jazz)",
  "Electric Guitar (clean)",
  "Electric Guitar (muted)",
  "Overdriven Guitar",
  "Distortion Guitar",
  "Guitar harmonics",
  ;; Basses
  "Acoustic Bass",
  "Electric Bass (finger)",
  "Electric Bass (pick)",
  "Fretless Bass",
  "Slap Bass 1",
  "Slap Bass 2",
  "Synth Bass 1",
  "Synth Bass 2",
  ;; Strings
  "Violin",
  "Viola",
  "Cello",
  "Contrabass",
  "Tremolo Strings",
  "Pizzicato Strings",
  "Orchestral Harp",
  "Timpani",
  ;; Ensemble strings and voices
  "String Ensemble 1",
  "String Ensemble 2",
  "SynthStrings 1",
  "SynthStrings 2",
  "Choir Aahs",
  "Voice Oohs",
  "Synth Voice",
  "Orchestra Hit",
  ;; Brass
  "Trumpet",
  "Trombone",
  "Tuba",
  "Muted Trumpet",
  "French Horn",
  "Brass Section",
  "SynthBrass 1",
  "SynthBrass 2",
  ;; Reeds
  "Soprano Sax",              ;; 64
  "Alto Sax",
  "Tenor Sax",
  "Baritone Sax",
  "Oboe",
  "English Horn",
  "Bassoon",
  "Clarinet",
  ;; Pipes
  "Piccolo",
  "Flute",
  "Recorder",
  "Pan Flute",
  "Blown Bottle",
  "Shakuhachi",
  "Whistle",
  "Ocarina",
  ;; Synth Leads
  "Lead 1 (square)",
  "Lead 2 (sawtooth)",
  "Lead 3 (calliope)",
  "Lead 4 (chiff)",
  "Lead 5 (charang)",
  "Lead 6 (voice)",
  "Lead 7 (fifths)",
  "Lead 8 (bass + lead)",
  ;; Synth Pads
  "Pad 1 (new age)",
  "Pad 2 (warm)",
  "Pad 3 (polysynth)",
  "Pad 4 (choir)",
  "Pad 5 (bowed)",
  "Pad 6 (metallic)",
  "Pad 7 (halo)",
  "Pad 8 (sweep)",
  ;; Effects
  "FX 1 (rain)",
  "FX 2 (soundtrack)",
  "FX 3 (crystal)",
  "FX 4 (atmosphere)",
  "FX 5 (brightness)",
  "FX 6 (goblins)",
  "FX 7 (echoes)",
  "FX 8 (sci-fi)",
  ;; Ethnic
  "Sitar",
  "Banjo",
  "Shamisen",
  "Koto",
  "Kalimba",
  "Bag pipe",
  "Fiddle",
  "Shanai",
  ;; Percussion
  "Tinkle Bell",
  "Agogo",
  "Steel Drums",
  "Woodblock",
  "Taiko Drum",
  "Melodic Tom",
  "Synth Drum",
  "Reverse Cymbal",
  ;; Sound Effects
  "Guitar Fret Noise",
  "Breath Noise",
  "Seashore",
  "Bird Tweet",
  "Telephone Ring",
  "Helicopter",
  "Applause",
  "Gunshot"
])

;; GM drum notes start at 35 (C), so subtrack gm-drum-note-lowest from your
;; note number before using this vector.
(def gm-drum-note-lowest 35)
;; General MIDI drum channel note names.
(def gm-drum-note-names [
  "Acoustic Bass Drum",       ;; 35, C
  "Bass Drum 1",              ;; 36, C#
  "Side Stick",               ;; 37, D
  "Acoustic Snare",           ;; 38, D#
  "Hand Clap",                ;; 39, E
  "Electric Snare",           ;; 40, F
  "Low Floor Tom",            ;; 41, F#
  "Closed Hi Hat",            ;; 42, G
  "High Floor Tom",           ;; 43, G#
  "Pedal Hi-Hat",             ;; 44, A
  "Low Tom",                  ;; 45, A#
  "Open Hi-Hat",              ;; 46, B
  "Low-Mid Tom",              ;; 47, C
  "Hi Mid Tom",               ;; 48, C#
  "Crash Cymbal 1",           ;; 49, D
  "High Tom",                 ;; 50, D#
  "Ride Cymbal 1",            ;; 51, E
  "Chinese Cymbal",           ;; 52, F
  "Ride Bell",                ;; 53, F#
  "Tambourine",               ;; 54, G
  "Splash Cymbal",            ;; 55, G#
  "Cowbell",                  ;; 56, A
  "Crash Cymbal 2",           ;; 57, A#
  "Vibraslap",                ;; 58, B
  "Ride Cymbal 2",            ;; 59, C
  "Hi Bongo",                 ;; 60, C#
  "Low Bongo",                ;; 61, D
  "Mute Hi Conga",            ;; 62, D#
  "Open Hi Conga",            ;; 63, E
  "Low Conga",                ;; 64, F
  "High Timbale",             ;; 65, F#
  "Low Timbale",              ;; 66, G
  "High Agogo",               ;; 67, G#
  "Low Agogo",                ;; 68, A
  "Cabasa",                   ;; 69, A#
  "Maracas",                  ;; 70, B
  "Short Whistle",            ;; 71, C
  "Long Whistle",             ;; 72, C#
  "Short Guiro",              ;; 73, D
  "Long Guiro",               ;; 74, D#
  "Claves",                   ;; 75, E
  "Hi Wood Block",            ;; 76, F
  "Low Wood Block",           ;; 77, F#
  "Mute Cuica",               ;; 78, G
  "Open Cuica",               ;; 79, G#
  "Mute Triangle",            ;; 80, A
  "Open Triangle"             ;; 81, A#
])
