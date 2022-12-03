(ns meshanka.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::route
 (fn [db _]
   (-> db :common/route)))

(rf/reg-sub
 ::page-id
 :<- [::route]
 (fn [route _]
   (-> route :data :name)))

(rf/reg-sub
 ::page
 :<- [::route]
 (fn [route _]
   (-> route :data :view)))

(rf/reg-sub
 ::docs
 (fn [db _]
   (:docs db)))

(rf/reg-sub
 ::error
 (fn [db _]
   (:common/error db)))

(rf/reg-sub
 ::users
 (fn [db _]
   (-> db :common/users)))
