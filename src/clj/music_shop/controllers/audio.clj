(ns music-shop.controllers.audio
  (:require [music-shop.layout :as layout]
            [music-shop.db.core :as db]))

(defn home-page [{session :session}]
  (layout/render "home.html" (merge {:user (get session :user)})))

(defn get-audios []
  (seq (db/get-all-audio)) )