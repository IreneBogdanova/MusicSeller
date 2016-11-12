(ns music_shop.core
  (:require
    [music_shop.home :as home]
    [ajax.core :refer [GET POST]]))

(defn init! []
  (if (not (nil? (js/document.getElementById "home-content")))
    (home/home-page)))