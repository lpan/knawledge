(ns knawledge.pages.sign-up
  (:require [reagent.core :as r]
            [ajax.core :refer [POST]]))

;; -------------------------
;; Components

(defn form-input [error {:keys [label] :as props}]
  (let [error? (some? error)]
    [:div.form-group {:class (when error? "has-danger")}
     [:label label]
     (when error? [:div.form-control-feedback error])
     [:input.form-control props]]))

(defn sign-up-form []
  (let [form-state (r/atom {:email {:error nil :value ""}
                            :password {:error nil :value ""}
                            :password-conf {:error nil :value ""}})

        preds {:email [["Email field cannot be empty"
                        #(not= 0 (count %))]]

               :password [["Password field cannot be empty"
                           #(not= 0 (count %))]
                          ["Password has to be at least 5 characters long"
                           #(<= 5 (count %))]]

               :password-conf [["Password confirmation does not match password"
                                #(= % (get-in @form-state [:password :value]))]]}

        validate! (fn [k]
                    (let [value (get-in @form-state [k :value])
                          predicates (get preds k)
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

        updater (fn [k] #(do (swap! form-state assoc-in [k :value] (.. % -target -value))
                             (validate! k)))

        submit #(let [forms @form-state
                      {:keys [email password]} forms]
                  ; check if there is any error in the state
                  (when (empty? (->> forms
                                     (map second)
                                     (map first)
                                     (map second)
                                     (filter some?)))
                    (POST "/api/auth" {:params {:email (:value email)
                                                :password (:value password)}})))]

    (fn []
      (let [{:keys [email password password-conf]} @form-state]
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
         [:button.btn.btn-primary.mt-1 {:on-click submit} "Sign up"]]))))

(defn sign-up-page []
  [:div.container.mt-2
   [sign-up-form]])
