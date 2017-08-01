(ns ^:figwheel-no-load knawledge.app
  (:require [knawledge.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
