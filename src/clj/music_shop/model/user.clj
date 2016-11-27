(ns music-shop.model.user
  (require [music-shop.db.core :as db]
           [buddy.hashers :as hashers]))

(defprotocol User
  (check-password [this pass]))

(defrecord Person [email password money role]
  User
  (check-password [this pass]
    (hashers/check pass password)))

(defn get-by-email [email]
  (map->Person (db/get-user {:email email})))

(def default-money-amount (float 0))

(def user-role-id (int 1))

(defn encrypt-password [password]
  (hashers/derive password {:alg :pbkdf2+sha256}))

(defn create-user [email password]
  (try
    (db/create-user! {:email   email
                      :pass    (encrypt-password password)
                      :money   default-money-amount
                      :role_id user-role-id})
    (Person. email password default-money-amount user-role-id)
    (catch Exception e
      (.println System/out e)
      (throw (Exception. "Email address already in use")))))

(def role {0 :guest 1 :user 2 :admin})