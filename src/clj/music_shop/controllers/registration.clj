(ns music-shop.controllers.registration
  (:require [music-shop.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [buddy.hashers :as hashers]
            [music-shop.db.core :as db]))


(defn login-page
  ([] (layout/render "login.html"))
  ([error] (layout/render "login.html" (merge {:error-message error}))))

(defn signup-page
  ([] (layout/render "signup.html"))
  ([error] (layout/render "signup.html" (merge {:error-message error}))))

(defn encrypt-password [password]
  (hashers/derive password {:alg :pbkdf2+sha256             ;salt is auto generated
                            }))
(defn check-password [password user-pwd]
  (hashers/check password user-pwd))

(defn check-user [email password]
  (if-let [user (db/get-user {:email email})]
    (if (check-password password (get-in user [:pass]))
      (dissoc user :password))))

(defn do-login [{{email :email password :password} :params
                 session                           :session}]
  (if-let [user (check-user email password)]
    (assoc
      (redirect "/home")
      :session (assoc session :user email))                 ; Add an :identity to the session
    (login-page "There is no user with such credentials")))

(defn create-user [email password]
  (try
    (db/create-user! {:email email
                      :pass  (encrypt-password password)})
    (catch Exception e
      (.println System/out e)
      (signup-page "Email address already in use"))))

(defn signup [{{email :email password :password verify :verify} :params}]
  (if (.equals verify password)
    (create-user email password)
    (signup-page "Password does not match the confirm password")))

(defn log-out [{session :session}]
  (assoc
    (redirect "/home")
    :session(dissoc session :user)))
