(ns music-shop.controllers.audio
  (:require [music-shop.layout :as layout]
            [music-shop.db.core :as db]
            [music-shop.controllers.basket :as basket]
            [music-shop.model.audio :as audiotape]))

(defn home-page [{session :session}]
  (layout/render "home.html" (merge {:user (get-in session [:user :email])})))

(defn add-to-basket [{{id :audio-id} :params
                      session        :session}]
  (if-let [user (get-in session [:user])]
    (db/add-to-basket! {:email (:email user) :audio_id id}))
  {:status  200
   :session (assoc session :audio-list (conj
                                         (basket/get-selected-from-session session) id))
   :body    {:id id}})

(defn get-audios [session]
  (let [audio-list (seq (db/get-all-audio))]
    (audiotape/get-audio-records audio-list (get session :audio-list ()))))

(defn save-new-audio [audio]
  (db/add-new-audio! (merge audio {:upload "2016-10-10 00:00:00" :path "audio.mp3" :playback 1})))

(defn save-audio [{{audio :audio} :params
                   session        :session}]
  (if (.equals (get audio :id) 0)
    (save-new-audio audio)
    (db/update-audio! audio))
  {:status  200
   :session session
   :body    true})