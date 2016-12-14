(ns music-shop.test.controller.basket
  (:require [music-shop.db.core :refer [*db*] :as db]
            [music-shop.controllers.basket :as basket]
            [clojure.test :refer :all]
            [music-shop.test.util :as util]))

(deftest test-add-to-basket
  (testing "With an empty list in the session"
    (util/assert-equals (basket/get-selected-from-session {}) ()))

  (testing "With not empty list the session"
    (util/assert-equals (basket/get-selected-from-session
                          {:audio-list (list 1 2)})
                        (list 1 2))))

(deftest test-remove-from-basket
  (with-redefs-fn
    {#'db/remove-from-basket! util/empty-function}
    #(let [response-list (get-in
                           (basket/remove-from-basket (util/create-request (list 1 2 3)))
                           [:session :audio-list])]
      (util/assert-equals response-list (set '(3 2))))))
