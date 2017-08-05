(ns knawledge.core
  (:require [reagent.core :as r]
            [knawledge.ajax :refer [load-interceptors!]]
            [knawledge.pages.core :refer [page navbar init-router!]]
            [ajax.core :refer [GET POST]]))

;; -------------------------
;; Initialize app

(defn mount-components []
  (r/render [#'navbar] (.getElementById js/document "navbar"))
  (r/render [#'page] (.getElementById js/document "app")))

(defn init! []
  (load-interceptors!)
  (init-router!)
  (mount-components))

(init!)
