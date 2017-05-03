(ns pmtest.pm
  (:require [pmtest.cursor :as cursor]))

;;; ================ messages and triggers ================

(defn send-message
  [pm name]
  (let [messages (get (:messages pm) name)]
    (when messages
      ;; TODO
      )))
