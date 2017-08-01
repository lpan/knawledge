(ns knawledge.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[knawledge started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[knawledge has shut down successfully]=-"))
   :middleware identity})
