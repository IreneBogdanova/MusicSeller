(ns music-shop.model.audio)

(defrecord Audiotape
  [id name artist upload price path listen_auth_only playback selected])

(defn create-audio [db-audio selected]
  (Audiotape.
    (:id db-audio) (:name db-audio) (:artist db-audio) (:upload db-audio) (:price db-audio)
    (:path db-audio) (:listen_auth_only db-audio) (:playback db-audio) selected))

(defn selected? [audio selected-list]
  (if-not (empty? selected-list)
    (.contains selected-list (:id audio))
    false))

; todo переписать на фильтр
(defn get-audio-records [db-list audio-list selected-list]
  (if (empty? db-list)
    audio-list
    (conj
      (let [rest (rest db-list)]
        (get-audio-records rest audio-list selected-list))
      (create-audio (first db-list) (selected? (first db-list) selected-list)))))



