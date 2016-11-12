(ns music-shop.controllers.audio
  (:require [music-shop.layout :as layout]
            [music-shop.db.core :as db]
            [music-shop.model.audio :refer (->Audiotape)])
  (:import (music_shop.model.audio Audiotape)))

(defn home-page [{session :session}]
  (layout/render "home.html" (merge {:user (get session :user)})))

(defn selected? [audio selected-list]
  (if-not (empty? selected-list)
    (.contains selected-list (:id audio))
    false))

(defn create-audio [audio-values selected-list]
  (Audiotape.
    (:id audio-values) (:name audio-values) (:artist audio-values)
    (:upload_date audio-values) (:path audio-values) (selected? audio-values selected-list)))

(defn get-audio-records [db-list audio-list selected-list]
  (if (empty? db-list)
    audio-list
    (conj
      (let [rest (rest db-list)]
        (get-audio-records rest audio-list selected-list))
      (create-audio (first db-list) selected-list))))

(defn get-audios [{session :session}]
  (let [audio-list (seq (db/get-all-audio))]
    (get-audio-records audio-list () (get session :audio-list ()))))



(defn save-id [session id]
  (conj (get session :audio-list ()) id))

(defn add-to-basket [{{id :audio-id} :params
                      session        :session}]
  {:status  200
   :session (assoc session :audio-list (save-id session id))
   :body    {:id id}})