(ns reagent-playground.util)

(defn map-values
  [m keys f & args]
  (reduce #(apply update-in %1 [%2] f args) m keys))

