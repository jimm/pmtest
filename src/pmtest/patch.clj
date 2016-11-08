(ns pmtest.patch
  (:require pmtest.connection))

(defn start
  [patch]
  (when-not (:running patch)
    (let [start-messages (:start-messages patch)]
      (map #(pmtest.connection/start % start-messages) (:connections patch)))
    (assoc patch :running true)))

(defn stop
  [patch]
  (when (and patch (:running patch))
    (let [stop-messages (:stop-messages patch)]
      (map #(pmtest.connection/stop % stop-messages) (:connections patch)))
    (assoc patch :running false)))
