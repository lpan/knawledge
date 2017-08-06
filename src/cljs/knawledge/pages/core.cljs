(ns knawledge.pages.core
  (:require [reagent.core :as r]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [knawledge.pages.home :refer [home-page]]
            [knawledge.pages.sign-up :refer [sign-up-page]]))

(def pages
  {:home #'home-page
   :sign-up #'sign-up-page})

(defn init-router!
  []
  (do (secretary/set-config! :prefix "#")
      (secretary/defroute "/" [] (session/put! :page :home))
      (secretary/defroute "/sign_up" [] (session/put! :page :sign-up))))

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
         [nav-link "#/" "Home" :home collapsed?]
         [nav-link "#/sign_up" "Sign up" :sign-up collapsed?]]]])))

(defn page []
  [(pages (session/get :page))])
