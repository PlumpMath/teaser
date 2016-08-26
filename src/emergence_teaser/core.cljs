(ns emergence-teaser.core
  (:require
   [reagent.core :as r]
   [thi.ng.geom.core :as g]
   [thi.ng.geom.circle :as c]
   [thi.ng.geom.core.vector :as v :refer [vec2 vec3]]
   [thi.ng.geom.svg.core :as svg]
   [thi.ng.geom.svg.adapter :as svgadapt]
   [thi.ng.geom.webgl.animator :refer [animate]]))

(enable-console-print!)

(def sin #(.sin js/Math %))
(def abs #(.abs js/Math %))
(def x (r/atom 0))

(defn draw-svg
  [width body]
  [:div
   (->> body
        (svgadapt/all-as-svg)
        (svgadapt/inject-element-attribs svgadapt/key-attrib-injector)
        (svg/svg {:width width :height width}))])

(defn main []
  (let [width 300
        pos (vec2 @x 0)]
    (->> (c/circle (g/madd pos [1 1] (/ width 2)) 10)
        (svg/group {:stroke "none" :fill (str "rgba(200, 50, 100, " (* 0.01 (abs @x)) ")")})
        (draw-svg width))))

(defn on-js-reload []
  (r/render
   [main]
   (js/document.getElementById "app"))
  (animate
   (fn [[t frame]]
    (reset! x (* 100 (sin t))))))

(on-js-reload)
