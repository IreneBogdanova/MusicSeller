(ns music_shop.home
  (:require [reagent.core :as r]
            [ajax.core :refer [GET POST]]))

(defn change-to-added [button]
  (set! (.-className button) "added-audio")
  (set! (.-title button) "Added")
  (set! (.-onclick button) ()))

(defn added-audio-handler [response]
  (let [button (js/document.getElementById (:id response))]
    (if-not (nil? button) (change-to-added button))))

(defn add-to-basket [id]
  (POST "/add-to-basket"
        {:headers {"Accept"       "application/transit+json"
                   "x-csrf-token" (.-value (.getElementById js/document "__anti-forgery-token"))}
         :params  {:audio-id id}
         :handler added-audio-handler}))

(def upload-path "/upload/")

(defn get-add-button-map [selected id]
  (if selected
    {:class "added-audio"}
    {:class "add-btn" :on-click #(add-to-basket id)}))

(defn create-add-btn [audio]
  [:button (merge
             {:id    (:id audio)
              :title "Add to the basket"}
             (get-add-button-map (:selected audio) (:id audio)))])

(defn audio-view [audio]
  [:tr.audio
   [:td.name {:class "audio-name"} (:name audio)]
   [:td.artist {:class "audio-artist"} (:artist audio)]
   [:td.audio [:audio {:src      (+ upload-path (:path audio))
                       :controls true}]]
   [:td.basket (create-add-btn audio)]])

(defn audio-list-view [audios]
  (into [:tbody.audio-list]
        (for [audio audios]
          [audio-view audio])))

(defn init-audio-table [audio-list]
  (let [audio-table (js/document.getElementById "audio-table")]
    (r/render [audio-list-view audio-list] audio-table)))

(defn home-page []
  (GET "/get-audios" {:handler init-audio-table}))