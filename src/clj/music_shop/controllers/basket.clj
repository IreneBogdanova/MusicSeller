(ns music-shop.controllers.basket
  (:require [music-shop.layout :as layout]
            [music-shop.db.core :as db]
            [music-shop.model.basket :as basket]))

(defn basket-page []
  (layout/render "basket.html"))

(defn get-selected-from-session [session]
  (get session :audio-list ()))

(defn get-selected-audio-from-db [email]
  (let [audio-map (db/get-selected-audios {:email email})]
    (map :audio_id audio-map)))

(defn merge-selected-and-db [email selected]
  (concat (get-selected-audio-from-db email) selected))

(defn load-selected-audios [session]
  (let [selected (get-selected-from-session session)]
    (if-let [email (get-in session [:user :email])]
      (merge-selected-and-db email selected)
      selected)))

(defn get-chosen-audios [audios-list]
  (if-not (empty? audios-list)
    (basket/create-basket (db/find-audios
                            {:ids audios-list :cols ["id" "name" "artist" "price"]}))))

(defn fill-page-data-map [session]
  (let [audio-list (load-selected-audios session)]
    (let [db-audio-list (get-chosen-audios audio-list)]
      (if-not (empty? db-audio-list)
        {:audios    (:basket db-audio-list)
         :total-sum (basket/get-total-sum db-audio-list)}
        {:audios    nil
         :total-sum nil}))))

(defn get-page-data [{session :session}]
  {:status  200
   :session session
   :body    (fill-page-data-map session)})

(defn remove-from-basket [{{audio-id :audio-id} :params
                           session              :session}]
  (.println System/out audio-id)
  (if-let [user (get-in session [:user])]
    (db/remove-from-basket! {:email (:email user) :audio_id audio-id}))
  {:status  200
   :session (assoc session :audio-list (disj (set (get-selected-from-session session)) audio-id))
   :body    {:id audio-id}})