(ns knawledge.pages.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [secretary.core :as secretary :include-macros true]
            [knawledge.pages.home :refer [home-page]])
  (:import goog.History))

(def pages
  {:home #'home-page})

;; -------------------------
;; History
;; must be called after routes have been defined

(defn hook-browser-navigation! []
  (doto (History.)
        (events/listen
          HistoryEventType/NAVIGATE
          (fn [event]
              (secretary/dispatch! (.-token event))))
        (.setEnabled true)))

(defn init-router!
  []
  (do (secretary/set-config! :prefix "#")
      (secretary/defroute "/" [] (session/put! :page :home))
      (hook-browser-navigation!)))

;; -------------------------
;; Components

(defn nav-link [uri title page collapsed?]
  [:li.nav-item
   {:class (when (= page (session/get :page)) "active")}
   [:a.nav-link
    {:href uri
     :on-click #(reset! collapsed? true)} title]])

(defn navbar []
  (let [collapsed? (r/atom true)]
    (fn []
      [:nav.navbar.navbar-dark.bg-primary
       [:button.navbar-toggler.hidden-sm-up
        {:on-click #(swap! collapsed? not)} "☰"]
       [:div.collapse.navbar-toggleable-xs
        (when-not @collapsed? {:class "in"})
        [:a.navbar-brand {:href "#/"} "knawledge"]
        [:ul.nav.navbar-nav
         [nav-link "#/" "Home" :home collapsed?]]]])))

(defn page []
  [(pages (session/get :page))])
