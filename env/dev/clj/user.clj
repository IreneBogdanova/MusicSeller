(ns user
  (:require [mount.core :as mount]
            music-shop.core))

(defn start []
  (mount/start-without #'music-shop.core/repl-server))

(defn stop []
  (mount/stop-except #'music-shop.core/repl-server))

(defn restart []
  (stop)
  (start))


