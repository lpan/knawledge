(ns knawledge.pages.sign-up
  (:require [reagent.core :as r]))

(defn render-error
  [state msgs]
  (if-not (empty? msgs)
    (swap! state assoc-in )))

;; -------------------------
;; Components

(defn form-input [error {:keys [label] :as props}]
  [:div.form-group
   [:label label]
   (if (nil? error) nil [:div.form-control-feedback error])
   [:input.form-control props]])

(defn sign-up-form []
  (let [form-state (r/atom {:email {:error nil :value ""}
                            :password {:error nil :value ""}
                            :password-conf {:error nil :value ""}})

        updater (fn [k] #(swap! form-state assoc-in [k :value] (.. % -target -value)))

        validate (fn [k predicates]
                   (let [value (get-in @form-state [k :value])
                         render-error (fn [msgs]
                                        (if (empty? msgs)
                                          (swap! form-state assoc-in [k :error] nil)
                                          (swap! form-state assoc-in [k :error] (first msgs))))]
                     (->> predicates
                          ; filter out valid
                          (filter (fn [[msg pred]] (not (pred value))))
                          ; map to error message
                          (map first)
                          ; clear error if it is nil
                          render-error)))

        email-preds [["Email field cannot be empty"
                      #(some? %)]]
        password-preds [["Password field cannot be empty"
                         #(some? %)]
                        ["Password has to be at least 5 characters long"
                         #(<= 5 (count %))]]
        password-conf-preds [["Password confirmation does not match password"
                              #(= % (get-in @form-state [:password :value]))]]]

    (fn []
      (let [{:keys [email password password-conf]} @form-state]
        (validate :email email-preds)
        (validate :password password-preds)
        (validate :password-conf password-conf-preds)
        [:form.px-3
         [form-input (:error email) {:label "Email address"
                                     :type "text"
                                     :on-change (updater :email)}]
         [form-input (:error password) {:label "Password"
                                        :type "password"
                                        :on-change (updater :password)}]
         [form-input (:error password-conf) {:label "Password confirmation"
                                             :type "password"
                                             :on-change (updater :password-conf)}]
         [:button.btn.btn-primary.mt-1 "Sign up"]]))))

(defn sign-up-page []
  [:div.container.mt-2
   [sign-up-form]])
