(ns testapp.views
  (:require
    [reagent.core :as reagent]
    [re-frame.core :refer [subscribe dispatch]]
    [testapp.subs :as subs]
    [testapp.events :as events]))

(defn- req [{id :db/id :req/keys [title description declarer performer due-date]}]
  [:tr
   ^{:key id}
   [:td title]
   [:td description]
   [:td declarer]
   [:td performer]
   [:td due-date]])

(defn- reqs []
  [:table
   [:tr
    [:th "Title"]
    [:th "Description"]
    [:th "Declarer"]
    [:th "Performer"]
    [:th "Due-date"]]
   (map #(req %) @(subscribe [::subs/reqs]))])

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
      [:form {:on-submit add}
       [:div.input-group.input-group-sm
        [:label "Title"
         [:input.form-control {:type       "text"
                               :value      (:req/title @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/title (-> % .-target .-value))}]]

        [:label "Description"
         [:input.form-control {:type       "text"
                               :value      (:req/description @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/description (-> % .-target .-value))}]]

        [:label "Declarer"
         [:input.form-control {:type       "text"
                               :value      (:req/declarer @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/declarer (-> % .-target .-value))}]]

        [:label "Performer"
         [:input.form-control {:type       "text"
                               :value      (:req/performer @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/performer (-> % .-target .-value))}]]

        [:label "Due-date"
         [:input.form-control {:type       "text"
                               :value      (:req/due-date @req)
                               :auto-focus false
                               :required   true
                               :maxLength  500
                               :on-change  #(swap! req assoc :req/due-date (-> % .-target .-value))}]]
        [:div.input-group-append
         [:button.btn.btn-outline-success {:type "submit"} "Add"]]]])))

(defn main-panel []
  [:div.container
   [:div.row.justify-content-center
    [:div.col-12
     [:div.box-body
      [add-req]
      [reqs]]]]])
