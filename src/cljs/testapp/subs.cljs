(ns testapp.subs
  (:require
   [re-frame.core :refer [reg-sub subscribe]]))

(reg-sub
  ::reqs
  (fn [{reqs :reqs}]
    (vals reqs)))
