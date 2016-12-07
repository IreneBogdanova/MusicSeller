-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO user
(email, pass, money, role_id)
VALUES (:email, :pass, :money, :role_id)

-- :name get-user :? :1
-- :doc retrieve a user given the email
SELECT
  email,
  pass    AS password,
  money,
  role_id AS role
FROM user
WHERE email = :email

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM user
WHERE id = :id

-- :name add-to-basket! :! :n
-- :doc creates a new user_basket record
INSERT INTO user_basket
(user_email, audio_id)
VALUES (:email, :audio_id)

-- :name remove-from-basket! :! :n
-- :doc delete a user_basket record by audio id and user email
DELETE FROM user_basket
WHERE audio_id = :audio_id and user_email = :email

-- :name get-selected-audios :? :*
-- :doc retrieve audios from user's basket
SELECT audio_id
FROM user_basket
WHERE user_email=:email

-- :name add-new-audio! :! :n
-- :doc creates a new audio record
INSERT INTO audio
(NAME, artist, upload_date, price, path, listen_auth_only, playback_type)
VALUES (:NAME, :artist, :upload, :price, :path, :listen_auth_only, :playback)

-- :name get-all-audio :? :*
-- :doc selects all available audio
SELECT
  id,
  name,
  artist,
  upload_date   AS upload,
  price,
  path,
  listen_auth_only,
  playback_type AS playback
FROM audio

-- :name find-audios :? :*
-- :doc find audios with a matching ID
SELECT :i*:cols
FROM audio
WHERE id IN (:v*:ids)

-- :name update-audio! :! :n
-- :doc update audio with a matching ID
UPDATE audio
SET name = :name, artist = :artist, price = :price, listen_auth_only = :listen_auth_only
WHERE id = :id