(ns music-shop.model.basket
  (require [music-shop.db.core :as db]))

(defprotocol HasTotalSum
  (get-total-sum [this]))

(defrecord Basket [basket]
  HasTotalSum
  (get-total-sum [this]
    (if-not (empty? basket)
      (->> basket
           (map :price)
           (seq)
           (reduce +))
      0)))

(defn create-basket [audios]
  (Basket. audios))