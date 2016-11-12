(ns pmtest.patch
  (:use [pmtest.connection :rename {start conn-start stop conn-stop}]))

(defn start
  [patch]
  (when-not (:running patch)
    (let [start-messages (:start-messages patch)]
      (map #(conn-start % start-messages) (:connections patch)))
    (assoc patch :running true)))

(defn stop
  [patch]
  (when (and patch (:running patch))
    (let [stop-messages (:stop-messages patch)]
      (map #(conn-stop % stop-messages) (:connections patch)))
    (assoc patch :running false)))
