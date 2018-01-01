(ns reagent-playground.accordion
  (:require [reagent.core :as reagent]
            [reagent-playground.util :refer [map-values]]))

(def state (reagent/atom {}))

(defn toggle-value [v]
  (bit-xor 1 v))

(defn new-panel-value [name]
  (->
    (toggle-value (get-in @state [name :state]))
    {0 "none"
     1 "block"}))

(defn display-value [m name]
  (prn "display-value " m)
  (get-in m [name :display]))

(defn all-keys-except [m k]
  (vec (filter #(not= % k) (keys m))))

(defn toggle [name]
  ;;(prn @state)
  (let [names (all-keys-except @state name)]
    (prn "names: " names)
    (swap! state map-values names #(merge (@state %) {:state 0 :display "none"}))
    (swap! state merge {name {:state   (toggle-value (get-in @state [name :state]))
                              :display (new-panel-value name)}})))

(defn accordion-panel [display-state name content]
  [:div {:class "panel"
         :style {:display (display-value display-state name)}} content])

(defn accordion-div [name content]
  (swap! state merge {name {:state 0 :display "none"}})
  (fn [name content]
    ;;(prn "accordion fn invoked...")
    [:div
     [:button {:type     "button"
               :class    "accordion"
               :value    name
               :on-click (fn [_] (toggle name))}
      [:strong name]]
     [accordion-panel @state name content]]))
