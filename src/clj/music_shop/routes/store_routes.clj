(ns music-shop.routes.store-routes
  (:require [music-shop.layout :as layout]
            [music-shop.controllers.audio :as audio]
            [music-shop.controllers.registration :as registr]
            [music-shop.controllers.basket :as basket]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn index-page []
  (layout/render "index.html"))

(defn about-page []
  (layout/render "about.html"))



(defroutes store-routes
           (GET "/" [] (index-page))

           (GET "/login" [] (registr/login-page))
           (POST "/login" request (registr/do-login request))
           (GET "/signup" [] (registr/signup-page))
           (POST "/signup" request (registr/signup request))
           (GET "/logout" request (registr/log-out request))

           (GET "/home" request (audio/home-page request))
           (GET "/get-audios" request (audio/get-audios request))
           (POST "/add-to-basket" request (audio/add-to-basket request))
           (GET "/basket" request (basket/basket-page request))
           (GET "/about" [] (about-page)))


