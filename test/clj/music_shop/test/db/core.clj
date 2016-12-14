(ns music-shop.test.db.core
  (:require [music-shop.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [music-shop.config :refer [env]]
            [mount.core :as mount]))

(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'music-shop.config/env
      #'music-shop.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))

(deftest test-users-creation
  (jdbc/with-db-transaction [t-conn *db*]
                            (jdbc/db-set-rollback-only! t-conn)
                            (is (= 1 (db/create-user!
                                       t-conn
                                       {:email   "sam.smith@example.com"
                                        :pass    "pass"
                                        :money   0
                                        :role_id 1})))
                            (is (= {:email      "sam.smith@example.com"
                                    :password       "pass"
                                    :money      0.0
                                    :role 1}
                                   (db/get-user t-conn {:email "sam.smith@example.com"})))))
