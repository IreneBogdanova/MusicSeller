(ns music-shop.routes.store-routes
  (:require [music-shop.layout :as layout]
            [music-shop.controllers.audio :as audio]
            [music-shop.controllers.registration :as registr]
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
           (POST "/login" request  (registr/do-login request))
           (GET "/signup" [] (registr/signup-page))
           (POST "/signup" request (registr/signup request))
           (GET "/logout" request (registr/log-out request))

           (GET "/home" request (audio/home-page request))
           (GET "/get-audios" [] (audio/get-audios))
           ;(POST "/add/:id" [id] (audio/add-to-basket (:id id)))
           (GET "/about" [] (about-page)))


