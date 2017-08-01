(ns knawledge.app
  (:require [knawledge.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
