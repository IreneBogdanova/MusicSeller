(ns music-shop.model.audio)

(defrecord Audiotape
  [id name artist upload price path listen_auth_only playback selected])

(defn create-audio [db-audio selected]
  (Audiotape.
    (:id db-audio) (:name db-audio) (:artist db-audio) (:upload db-audio) (:price db-audio)
    (:path db-audio) (:listen_auth_only db-audio) (:playback db-audio) selected))

(defn mark-selected-audios [audios selected-ids-set]
  (map #(assoc % :selected (contains? selected-ids-set (:id %))) audios))

(defn get-audio-records [audios-from-db selected-ids]
  (let [ids-set (into #{} selected-ids)]
    (map #(map->Audiotape %) (mark-selected-audios audios-from-db ids-set))))



