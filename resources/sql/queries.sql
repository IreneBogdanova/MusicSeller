-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO user
(email, pass)
VALUES (:email, :pass)

-- :name get-user :? :1
-- :doc retrieve a user given the email
SELECT * FROM user
WHERE email = :email

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM user
WHERE id = :id

-- :name get-all-audio :? :*
-- :doc selects all available audio
SELECT * FROM audio

-- :name find-audios :? :*
-- :doc find audios with a matching ID
SELECT * FROM audio
WHERE id IN (:v*:ids)