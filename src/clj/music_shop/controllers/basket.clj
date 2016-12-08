(ns music-shop.controllers.basket
  (:require [music-shop.layout :as layout]
            [music-shop.db.core :as db]
            [music-shop.model.basket :as basket]
            [clojure.java.io :as io])
  (:import (java.util.zip ZipOutputStream ZipEntry)))

(defn basket-page []
  (layout/render "basket.html"))

(defn get-selected-from-session [session]
  (get session :audio-list ()))

(defn get-selected-audio-from-db [email]
  (let [audio-map (db/get-selected-audios {:email email})]
    (map :audio_id audio-map)))

(defn merge-selected-and-db [email selected]
  (concat (get-selected-audio-from-db email) selected))

(defn load-selected-audios [session]
  (let [selected (get-selected-from-session session)]
    (if-let [email (get-in session [:user :email])]
      (merge-selected-and-db email selected)
      selected)))

(defn get-chosen-audios [audios-list]
  (if-not (empty? audios-list)
    (basket/create-basket (db/find-audios
                            {:ids audios-list :cols ["id" "name" "artist" "price"]}))))

(defn fill-page-data-map [session]
  (let [audio-list (load-selected-audios session)]
    (let [db-audio-list (get-chosen-audios audio-list)]
      (if-not (empty? db-audio-list)
        {:audios    (:basket db-audio-list)
         :total-sum (basket/get-total-sum db-audio-list)}
        {:audios    nil
         :total-sum nil}))))

(defn get-page-data [{session :session}]
  {:status  200
   :session session
   :body    (fill-page-data-map session)})

(defn remove-from-basket [{{audio-id :audio-id} :params
                           session              :session}]
  (if-let [user (get-in session [:user])]
    (db/remove-from-basket! {:email (:email user) :audio_id audio-id}))
  {:status  200
   :session (assoc session :audio-list (disj (set (get-selected-from-session session)) audio-id))
   :body    {:id audio-id}})

(defn get-partiated-list [session]
  (partition 10 10 [] (get-selected-from-session session)))

(defn fff []
  (with-open [zip (ZipOutputStream. (io/output-stream "foo.zip"))]
    (doseq [f (file-seq (io/file "/path/to/directory")) :when (.isFile f)]
      (.putNextEntry zip (ZipEntry. (.getPath f)))
      (io/copy f zip)
      (.closeEntry zip))))

(defn get-selected-audios-paths [selected-ids]
  (map :path (db/find-audios {:ids selected-ids :cols ["path"]})))

(def path-to-dir "D://Univer/Functional programming/MusicSeller/resources/public/upload/")

(defn create-zip-link [selected-audio-list index]
  (let [audios-path-list (get-selected-audios-paths selected-audio-list)]
    (clojure.pprint/pprint audios-path-list)
    (let [zip-file (io/file (str path-to-dir "dld" index ".zip"))]
      (.createNewFile zip-file)
      (with-open [zip (ZipOutputStream. (io/output-stream zip-file))]
        (doseq [path audios-path-list]
          (let [f (io/file (str path-to-dir path))]
            (.putNextEntry zip (ZipEntry. (.getName f)))
            (io/copy f zip)
            (.closeEntry zip))))
      (str  "/upload/dld" index ".zip"))))

(defn order [{session :session}]
  "Creates downoad links for the order"
  (let [ordered-audios-list (get-partiated-list session)]
    (let [zip-links (agent ())]
      (doseq [tmp-list ordered-audios-list]
        (send-off zip-links conj
                  (create-zip-link tmp-list (.indexOf ordered-audios-list tmp-list))))
      (await zip-links)
      (layout/render "order.html"
                     (merge {:links @zip-links})))))