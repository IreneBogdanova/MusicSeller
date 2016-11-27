(ns music-shop.routes.store-routes
  (:require [music-shop.layout :as layout]
            [music-shop.controllers.audio :as audio]
            [music-shop.controllers.registration :as registr]
            [music-shop.controllers.basket :as basket]
            [music-shop.model.user :as user]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn index-page []
  (layout/render "index.html"))

(defn about-page []
  (layout/render "about.html"))

(defn get-home-page-data [{session :session}]
  {:status  200
   :session session
   :body    {:role       (get user/role (get-in session [:user :role] 0))
             :audio-list (audio/get-audios session)}})

(defroutes store-routes
           (GET "/" [] (index-page))

           (GET "/login" [] (registr/login-page))
           (POST "/login" request (registr/do-login request))
           (GET "/signup" [] (registr/signup-page))
           (POST "/signup" request (registr/signup request))
           (GET "/logout" request (registr/log-out request))

           (GET "/get-home-page-data" request (get-home-page-data request))
           (GET "/home" request (audio/home-page request))
           (POST "/save-audio" request (audio/save-audio request))
           (POST "/add-to-basket" request (audio/add-to-basket request))
           (GET "/basket" request (basket/basket-page request))
           (GET "/about" [] (about-page)))


