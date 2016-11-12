(ns music-shop.model.user
  (require  [music-shop.db.core :as db]))

(defrecord User [email password money])

(defn get-by-email [email]
  (let [user (db/get-user {:email email})]
    (User. (get user :email) (get user :passw) (get user :money))))