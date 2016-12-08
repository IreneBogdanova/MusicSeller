(ns music_shop.basket
  (:require [reagent.core :as reagent]
            [ajax.core :refer [GET POST]]))

(def basket-audios (reagent/atom ()))
(def total-sum (reagent/atom 0))

(defn get-total-sum []
  (if-not (empty? @basket-audios)
    (->> @basket-audios
         (map :price)
         (seq)
         (reduce +))
    0))

(defn deleted-audio-handler [{removed-id :id}]
  (swap! basket-audios #(remove (fn [audio] (= (:id audio) removed-id)) %))
  (reset! total-sum (get-total-sum)))

(defn get-post-header []
  {"Accept"       "application/transit+json"
   "x-csrf-token" (.-value (.getElementById js/document "__anti-forgery-token"))})

(defn delete-from-basket [id]
  (POST "/remove-from-basket"
        {:headers (get-post-header)
         :params  {:audio-id id}
         :handler deleted-audio-handler}))

(defn create-remove-btn [id]
  [:a
   {:class    "btn"
    :on-click #(delete-from-basket id)}
   "remove"])

(defn audio-view [audio]
  "Create one audio view"
  [:tr.audio {:id (:id audio)}
   [:td.name (:name audio)]
   [:td.price (:price audio)]
   [:td (create-remove-btn (:id audio))]])

(defn build-audio-table []
  [:table.basket-table
   [:thead
    [:th "Chosen audiotape"]
    [:th "Price"]
    [:th]]
   [:tbody
    (for [audio @basket-audios]
      ^{:key audio} [audio-view audio])
    [:tr.total
     [:td {:colSpan 3}
      [:em "Total sum : "] @total-sum]]]])

(defn audio-list-view []
  [:div.basket-content
   (if-not (empty? @basket-audios)
     [:div
      [:div.header "Your selected audios:"]
      [build-audio-table]
      [:a {:href "/order-basket"} "Order"]]
     [:p "There are no songs selected yet"])])

(defn init-basket-table [{audios :audios total :total-sum}]
  "Create view of audios, passed in response"
  (reset! basket-audios audios)
  (reset! total-sum total)
  (let [basket-content (.getElementById js/document "basket-content")]
    (reagent/render [audio-list-view] basket-content)))

(defn basket-page []
  (GET "/get-basket-page-data" {:handler init-basket-table}))