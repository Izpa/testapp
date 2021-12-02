(ns testapp.views
  (:require
    [reagent.core :as reagent]
    [re-frame.core :refer [subscribe dispatch]]
    [testapp.subs :as subs]
    [testapp.events :as events]))

(defn- req [{:req/keys [title description declarer performer due-date]}]
  [:div
   [:div [:div "Title"] [:div title]]
   [:div [:div "Description"] [:div description]]
   [:div [:div "Declarer"] [:div declarer]]
   [:div [:div "Performer"] [:div performer]]
   [:div [:div "Due-date"] [:div due-date]]])

(defn- reqs []
  [:div.box-body
   [:div
    (map #(^{:key (:db/id %)} [req %])
         @(subscribe [::subs/reqs]))]])

(defn- add-req []
  (let [req-default {:req/title       ""
                     :req/description ""
                     :req/declarer    ""
                     :req/performer   ""
                     :req/due-date    ""}
        req (reagent/atom req-default)
        add #((.preventDefault %)
              (dispatch [::events/add-req @req])
              (reset! req req-default))]
    (fn []
      [:div.box-footer
       [:form {:on-submit add}
        [:div.input-group.input-group-sm
         [:input.form-control {:type       "text"
                               :label      "Title"
                               :value      (:req/title @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/title (-> % .-target .-value))}]

         [:input.form-control {:type       "text"
                               :label      "Description"
                               :value      (:req/description @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/description (-> % .-target .-value))}]

         [:input.form-control {:type       "text"
                               :label      "Declarer"
                               :value      (:req/declarer @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/declarer (-> % .-target .-value))}]

         [:input.form-control {:type       "text"
                               :label      "Performer"
                               :value      (:req/performer @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/performer (-> % .-target .-value))}]

         [:input.form-control {:type       "text"
                               :label      "Due-date"
                               :value      (:req/due-date @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/due-date (-> % .-target .-value))}]
         [:div.input-group-append
          [:button.btn.btn-outline-success {:type "submit"} "Add"]]]]])))

(defn main-panel []
  [:div.container
   [:div.row.justify-content-center
    [:div.col-12
     [:div
      [reqs]
      [add-req]]]]])
