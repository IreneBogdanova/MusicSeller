(ns music-shop.test.util)

(defn empty-function [param])

(defn create-request [audio-list]
  {:params  {:audio-id 1}
   :session {:user       {:email "e@example.com"}
             :audio-list audio-list}})

(defmacro assert-equals [actual expected]
  `(let [~'actual-value ~actual]
     (when-not (= ~'actual-value ~expected)
       (throw
         (AssertionError.
           (str "Expected '" '~actual "' to be " ~expected " but was " ~'actual-value))))))