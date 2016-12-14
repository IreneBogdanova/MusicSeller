(ns music-shop.test.controller.audio
  (:require [music-shop.db.core :refer [*db*] :as db]
            [music-shop.controllers.audio :as audio]
            [clojure.test :refer :all]
            [music-shop.test.util :as util]))

(deftest test-add-to-basket
  (with-redefs-fn
    {#'db/add-to-basket! util/empty-function}
    #(let [response-list (get-in
                           (audio/add-to-basket (util/create-request ()))
                           [:session :audio-list])]
      (util/assert-equals response-list (list 1))))

  (with-redefs-fn
    {#'db/add-to-basket! util/empty-function}
    #(let [response-list (get-in
                           (audio/add-to-basket (util/create-request (list 2 3)))
                           [:session :audio-list])]
      (util/assert-equals response-list (list 1 2 3)))))
