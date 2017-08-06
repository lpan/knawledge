(ns knawledge.pages.sign-up)

;; -------------------------
;; Components

(defn form-input [label]
  [:div.form-group
   [:label label]
   [:input.form-control]])

(defn sign-up-page []
  [:div.container
   [:form
    [form-input "Email address"]]])
