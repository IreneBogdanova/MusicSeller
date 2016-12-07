(ns music-shop.controllers.registration
  (:require [music-shop.layout :as layout]
            [ring.util.response :refer [response redirect]]
            [music-shop.controllers.basket :as basket]
            [music-shop.db.core :as db]
            [music-shop.model.user :as user]))

(defn login-page
  ([] (layout/render "login.html"))
  ([error] (layout/render "login.html" (merge {:error-message error}))))

(defn signup-page
  ([] (layout/render "signup.html"))
  ([error] (layout/render "signup.html" (merge {:error-message error}))))

(defn check-user [email password]
  (if-let [user-to-login (user/get-by-email email)]
    (if (.check-password user-to-login password) user-to-login)))

(defn prepare-session-and-redirect [session user]
  (let [session-with-user (merge session {:user (dissoc user :password)})]
    (assoc
      (redirect "/home")
      :session  (merge session-with-user
                       {:audio-list (basket/load-selected-audios session-with-user)}))))

(defn do-login [{{email :email password :password} :params
                 session                           :session}]
  (if-let [user (check-user email password)]
    (prepare-session-and-redirect session user)
    (login-page "There is no user with such credentials")))

(defn signup [{{email :email password :password verify :verify} :params}]
  (if (.equals verify password)
    (try
      (user/create-user email password)
      (redirect "/")
      (catch Exception e
        (signup-page (.getMessage e))))
    (signup-page "Password does not match the confirm password")))

(defn log-out [{session :session}]
  (assoc
    (redirect "/home")
    :session (dissoc session :user)))
