(ns knawledge.routes.api
  (:require [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [buddy.hashers :as hashers]
            [knawledge.db.core :refer [create-user!]]
            [mount.core :as mount]))

(defn current-timestamp [] (str (System/currentTimeMillis)))

(defroutes user-create
  (POST "/api/user" [email username password]
        (try
          (create-user! {:id (current-timestamp)
                         :username username
                         :email email
                         :pass (hashers/derive password)})
          (response/ok {:status :ok})
          (catch Exception e
            (response/internal-server-error
              {:errors {:server-error ["Failed to create user!"]}})))))
