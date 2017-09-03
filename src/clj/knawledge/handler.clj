(ns knawledge.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [knawledge.layout :refer [error-page]]
            [knawledge.routes.home :refer [home-routes]]
            [knawledge.routes.api :as api-routes]
            [compojure.route :as route]
            [knawledge.env :refer [defaults]]
            [mount.core :as mount]
            [knawledge.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    (-> #'api-routes/user-create
        (wrap-routes middleware/wrap-formats))
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app [] (middleware/wrap-base #'app-routes))
