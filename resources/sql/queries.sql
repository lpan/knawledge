-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, username, email, pass)
VALUES (:id, :username, :email, :pass)

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id
