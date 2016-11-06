(ns music-shop.controllers.audio
  (:require [music-shop.layout :as layout]
            [music-shop.db.core :as db]))

(defn home-page []
  (layout/render "home.html"))

(defn get-audios []
  (seq (db/get-all-audio)) )