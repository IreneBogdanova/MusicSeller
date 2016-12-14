(ns music-shop.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [music-shop.handler :refer :all]
            [music-shop.test.util :as util]))

(defn get-response [url]
  ((app) (request :get url)))

(defn check-correct-response [url]
  (let [response (get-response url)]
    (util/assert-equals 200 (:status response))))

(deftest test-app-routes
  (testing "Index route"
    (check-correct-response "/"))

  (testing "Home route"
    (check-correct-response "/home"))

  (testing "Log in route"
    (check-correct-response "/login"))

  (testing "Sign up route"
    (check-correct-response "/signup"))

  (testing "Not-found route"
    (let [response (get-response "/invalid")]
      (is (= 404 (:status response))))))
