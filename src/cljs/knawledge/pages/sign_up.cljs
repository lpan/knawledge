(ns knawledge.pages.sign-up)

;; -------------------------
;; Components

(defn form-input [label props]
  [:div.form-group
   [:label label]
   [:input.form-control props]])

(defn sign-up-page []
  [:div.container.mt-2
   [:form.px-3
    [form-input "Email address" {:type "text"}]
    [form-input "Password" {:type "password"}]
    [form-input "Password confirmation" {:type "password"}]
    [:button.btn.btn-primary.mt-1 "Sign up"]]])
