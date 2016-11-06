(ns music-shop.routes.home
  (:require [music-shop.layout :as layout]
            [music-shop.controllers.audio :as audio]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn index-page []
  (layout/render "index.html"))

(defn about-page []
  (layout/render "about.html"))

(defroutes store-routes
           (GET "/" [] (index-page))
           (GET "/home" [] (audio/home-page))
           (GET "/get-audios" [] (audio/get-audios))
           ;(POST "/add/:id" [id] (audio/add-to-basket (:id id)))
           (GET "/about" [] (about-page)))


