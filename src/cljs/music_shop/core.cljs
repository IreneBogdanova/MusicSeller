(ns music_shop.core
  (:require
    [music_shop.home :as home]
    [music_shop.basket :as basket]
    [ajax.core :refer [GET POST]]))

(defn init! []
  (if-not (nil? (js/document.getElementById "home-content"))
    (home/home-page))
  (if-not (nil? (js/document.getElementById "basket-content"))
    (basket/basket-page)))