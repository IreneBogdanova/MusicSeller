(ns music_shop.core
  (:require
    [music_shop.home :as home]
    [ajax.core :refer [GET POST]]))

(defn init! []
  (home/home-page))