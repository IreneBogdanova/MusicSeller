(ns music_shop.audio-popup
  (:require [reagent-forms.core :refer [bind-fields]]
            [reagent.core :as reagent]
            [reagent-modals.modals :as reagent-modals]
            [ajax.core :refer [GET POST]]))

(def empty-audio-model {:id 0 :name "" :artist "" :price 0 :listen_auth_only false :file nil})


(defn saved-audio-handler [request]
  (.log js/console request)
  (reagent-modals/close-modal!))

(defn save-audio [audio]
  (.log js/console (str "save - " audio))
  (POST "/save-audio"
        {:headers {"Accept"       "application/transit+json"
                   "x-csrf-token" (.-value (.getElementById js/document "__anti-forgery-token"))}
         :params  {:audio audio}
         :handler saved-audio-handler}))

(defn form-template [isAdd]
  [:form
   [:input {:type "hidden" :name "id" :id :id}]
   ; (if isAdd
   ; [:input {:field :file :id :file :required true :accept "audio/mp3"}])
   [:div.form-group [:input.form-control {:field       :text
                                          :id          :name
                                          :placeholder "Name"
                                          :required    true}]]
   [:div.form-group [:input.form-control {:field       :text
                                          :id          :artist
                                          :placeholder "Artist"
                                          :required    true}]]
   [:div.form-group [:input.form-control {:field    :numeric
                                          :id       :price
                                          :required true}]]
   [:div.form-group [:label "Allow listening for authorized users only"
                     [:input {:field    :checkbox
                              :id       :listen_auth_only
                              :required true}]]]])

(defn get-title [isAdd]
  (if isAdd
    "Create new"
    "Edit"))

(defn get-modal-header [title]
  [:div.modal-header
   [:button.close {:type "button" :data-dismiss "modal"} "X"]
   [:h4.modal-title (+ title " audio")]])

(defn get-modal-footer [model]
  [:div.modal-footer
   [:button {:type "button" :class "btn btn-default" :data-dismiss "modal"} "Cancel"]
   [:button {:type     "submit"
             :class    "btn btn-default"
             :on-click #(save-audio @model)} "Save "]])

(defn get-audio-popup [isAdd audio]
  (.log js/console (str audio))
  (let [model (reagent/atom audio)]
    [:div
     (get-modal-header (get-title isAdd))
     [:div.modal-body [bind-fields (form-template isAdd) model]]
     (get-modal-footer model)]))

(defn get-edit-audio-modal [audio]
  "Create popup to edit audio"
  (get-audio-popup false audio))

(defn get-add-audio-modal []
  "Create popup to add new audio"
  (get-audio-popup true empty-audio-model))