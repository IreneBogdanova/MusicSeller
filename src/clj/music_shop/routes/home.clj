(ns music-shop.routes.home
  (:require [music-shop.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn index-page []
  (layout/render "index.html"))

(defn home-page []
  (layout/render "home.html"))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (index-page))
  (GET "/home" [] (home-page))
  (GET "/about" [] (about-page)))

