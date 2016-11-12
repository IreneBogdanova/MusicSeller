(ns music-shop.controllers.basket
  (:require [music-shop.layout :as layout]
            [music-shop.db.core :as db]))

(comment (if (empty? audios-list)
           (())
           (db/find-audios {:ids audios-list})))

(defn get-chosen-audios [audios-list]
  (.println System/out (str audios-list))
  (if-not (empty? audios-list)
          (db/find-audios {:ids audios-list, :cols ["name" "artist" "price"]})))

(defn get-total-sum [audio-list]
  (if-not (empty? audio-list)
          (->> audio-list
               (map :price)
               (reduce +))))

(defn basket-page [{session :session}]
  (let [audio-list (get session :audio-list ())]
    (.println System/out (str audio-list))
    (let [db-audio-list (get-chosen-audios audio-list)]
      (.println System/out (str db-audio-list))
      (layout/render "basket.html"
                     (merge
                       {:audios    db-audio-list
                        :total-sum (get-total-sum db-audio-list)})))))