(ns music_shop.home
  (:require [reagent.core :as r]
            [ajax.core :refer [GET POST]]))
(defn add-to-basket []
  (.log js/console "log"))

(def upload-path "/upload/")

(defn audio-view [audio]
  [:tr.audio
   [:td.name {:class "audio-name"} (:name audio)]
   [:td.artist {:class "audio-artist"} (:artist audio)]
   [:td.audio [:audio {:src      (+ upload-path (:path audio))
                       :controls true}]]
   [:td.basket [:button {:class "add-btn" :title "Add to the basket"}]]])

(defn audio-list-view [audios]
  (into [:tbody.audio-list]
        (for [audio audios]
          [audio-view audio])))

(defn init-audio-table [audio-list]
  (let [audio-table (js/document.getElementById "audio-table")]
    (r/render [audio-list-view audio-list] audio-table)))

(defn home-page []
  (GET "/get-audios" {:handler init-audio-table}))