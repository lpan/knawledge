(ns user
  (:require [mount.core :as mount]
            [knawledge.figwheel :refer [start-fw stop-fw cljs]]
            knawledge.core))

(defn start []
  (mount/start-without #'knawledge.core/repl-server))

(defn stop []
  (mount/stop-except #'knawledge.core/repl-server))

(defn restart []
  (stop)
  (start))


