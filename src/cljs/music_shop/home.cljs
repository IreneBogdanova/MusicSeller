(ns music_shop.home
  (:require [reagent.core :as reagent]
            [reagent-modals.modals :as reagent-modals]
            [ajax.core :refer [GET POST]]
            [music_shop.audio-popup :as audio-popup]))

(def role-admin :admin)
(def upload-path "/upload/")
(defn is-user-admin [role]
  "Check if user is admin"
  (= role role-admin))

(defn change-to-added [button]
  "Change 'Add' button view to 'Added' one "
  (set! (.-className button) "added-audio")
  (set! (.-title button) "Added")
  (set! (.-onclick button) ()))

(defn added-audio-handler [response]
  (let [button (.getElementById js/document (:id response))]
    (if-not (nil? button) (change-to-added button))))

(defn add-to-basket [id]
  (POST "/add-to-basket"
        {:headers {"Accept"       "application/transit+json"
                   "x-csrf-token" (.-value (.getElementById js/document "__anti-forgery-token"))}
         :params  {:audio-id id}
         :handler added-audio-handler}))

(defn edit-button [audio]
  [:button {:class    "edit-audio btn"
            :on-click #(reagent-modals/modal!
                        (audio-popup/get-edit-audio-modal audio))}])

(defn get-add-button-map [selected id]
  (if selected
    {:class "added-audio btn"}
    {:class "add-btn btn" :on-click #(add-to-basket id)}))

(defn add-btn [audio]
  "Add audio to the basket button"
  [:button (merge
             {:id    (:id audio)
              :title "Add to the basket"}
             (get-add-button-map (:selected audio) (:id audio)))])

(defn audio-view [audio is-admin]
  "Create one audio view"
  [:tr.audio
   [:td.name {:class "audio-name"} (:name audio)]
   [:td.artist {:class "audio-artist"} (:artist audio)]
   [:td.audio [:audio {:src      (+ upload-path (:path audio))
                       :controls true}]]
   [:td.buttons
    (add-btn audio)
    (if is-admin (edit-button audio))]])

(defn audio-list-view [{role :role audios :audio-list}]
  [:div.info-content
   (if (is-user-admin role)
     [:button.add {:class    "btn"
                   :on-click #(reagent-modals/modal! (audio-popup/get-add-audio-modal))}
      "Add new audio"])
   [:table {:id "audio-table"}
    [:tbody.audio-list
     (for [audio audios]
       ^{:key audio} [audio-view audio (is-user-admin role)])]]
   [reagent-modals/modal-window]])

(defn init-audio-table [response]
  "Create view of audios, passed in response"
  (let [audio-info (.getElementById js/document "audio-info")]
    (reagent/render [audio-list-view response] audio-info)))

(defn home-page []
  (GET "/get-home-page-data" {:handler init-audio-table}))