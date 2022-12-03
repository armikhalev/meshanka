(ns meshanka.pages.views.conjugator.view
  (:require
   [meshanka.subs :as subs]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [clojure.string :as str]
   [goog.string :as gstr]))

(defn present-tense
  [{:keys [base but-type? verb]}]
  [:div#present-tense.block
   [:h4 "Nastoječi čas / Present tense"]
   [:div.present-tense
    [:table.present-tense-table
     [:thead
      [:tr
       [:th ""]
       [:th "Jedinistveni lik / Singular "]
       [:th ""]
       [:th "Množistveni lik / Plural"]]]
     [:tbody
      [:tr.person1
       [:td "ja"]
       [:td (if but-type? "jesim, sim, je" (str base "m"))]
       [:td "mi"]
       [:td (if but-type? "jesmó, smo, je" (str base "mo"))]]
      [:tr.person2
       [:td "ti"]
       [:td (if but-type? "jesí, si, je" (str base "š"))]
       [:td "vi"]
       [:td (if but-type? "jesté, ste, je" (str base "te"))]]
      [:tr.person3
       [:td "on"]
       [:td (if but-type? "jesé, je" verb)]
       [:td "oni"]
       [:td (if but-type? "jesó, so, je" (str base "jo"))]]]]]])

(defn past-imperfective
  [{:keys [base verb-type]}]
  ;; if -va don't need to add it, otherwise just add -va
  (let [root (if (= verb-type :va)(.slice base 0 -2)(.slice base 0 -1))
        ending (if (= verb-type :va)(.slice root -1)(.slice base -1))
        stressed-vowel (case ending "a" "á" "e" "é" "u" "ú" "o" "ó" "i" "í" ending)]
    (case  verb-type
      :but [:div "buva"]
      :va [:div (str (.slice root 0 -1) stressed-vowel "va")]
      [:div (str root stressed-vowel "va")])))

(defn past-perfective
  [{:keys [base verb-type]}]
  (let [root (if (= verb-type :va)(.slice base 0 -2)(.slice base 0 -1))
        ending (if (= verb-type :va)(.slice root -1)(.slice base -1))
        stressed-vowel (case ending "a" "á" "e" "é" "u" "ú" "o" "ó" "i" "í" ending)
        stem (.slice root 0 -1)]
    (case  verb-type
      :iti [:div "šed"]
      :but [:div "be"]
      :va [:div (str stem stressed-vowel)]
      :ji  [:div (str root "í")]
      :je  [:div (str root "é")]
      :ja  [:div (str stem "í")]
      :ga->že [:div (str stem "žé")]
      :ka->če [:div (str stem "čé")]
      :xa->še [:div (str stem "šé")]
      :ša->se [:div (str stem "sé")]
      [:div (str root stressed-vowel)])))

;; (defn future-imperfective
  ;; [{:keys [verb-type base]}]
  ;; (let [past-perf [past-perfective props]])
  ;; (case  verb-type
  ;;   :iti [:div "iti"]
  ;;   :but [:div "but"]
  ;;   :mogči [:div "mogči"]
  ;;   [:div (str base "t")])
  ;; )
;; LOGIC ;;

(defn find-verb-type
  "Conditionally applies Meshanka rules to determine given verb's type."
  [verb]
  (if (= verb "ide")
    :iti
    (condp #(gstr/endsWith %2 %1) verb
      "može"  :mogči
      "ovaje" :ova
      "vaje"  :va
      "ji"    :ji
      "je"    :>> #(if (> (count verb) 2) :je :but)
      "ja"    :ja
      "ga"    :ga->že
      "ka"    :ka->če
      "xa"    :xa->še
      "ša"    :ša->se
      "e"     :e
      "i"     :i
      "a"     :a
      nil)))

(defn imperfective-infinitive
  [{:keys [verb-type base]}]
  (case  verb-type
    :iti [:div "iti"]
    :but [:div "but"]
    :mogči [:div "mogči"]
    [:div (str base "t")]))

(defn perfective-infinitive
  [{:keys [verb-type base] :as props}]
  (let [past-perf [past-perfective props]]
    (case  verb-type
      :iti [:div "iti"]
      :but [:div "but"]
      :mogči [:div "mogči"]
      [:div (str past-perf "t")])))

(defn input-field [v]
  [:div#input-conjugator
   [:input {:type "text"
            :on-change #(reset! v (-> % .-target .-value))}]])

;; MAIN ;;

(defn page []
  (let [!v (r/atom "pisavaje")]
    (fn []
      (let [v3person-sg (gstr/trim @!v)
            verb-type (find-verb-type (gstr/trim v3person-sg))
            exception-ending (case verb-type :je "je" :ji "ji" :ova "je" :va "je" nil)
            props {:base (if exception-ending (first (str/split v3person-sg exception-ending)) v3person-sg)
                   :verb v3person-sg
                   :verb-type verb-type
                   :but-type? (= :but verb-type)}]
        [:section.section>div.container>div.content
         [:h2 "Spregalnik / Conjugator"]
         [input-field !v]
         [:hr]
         (when (not (str/blank? @!v))
           [:div
            [:div
             [:h4 "Infinitiv"]
             [:div.block
              [:h6 "Nesoveršeni Vid / Imperfective Aspect"]
              [imperfective-infinitive props]]
             [:div.block
              [:h6 "Soveršeni Vid / Perfective Aspect"]
              [perfective-infinitive props]]
             ]
            [:hr]
            [present-tense props]
            [:hr]
            [:div
             [:h4 "Past tense"]
             [:div.block
              [:h6 "Nesoveršeni Vid / Imperfective Aspect"]
              [past-imperfective props]]
             [:div.block
              [:h6 "Soveršeni Vid / Perfective Aspect"]
              [past-perfective props]]]
            [:hr]
            [:div
             [:h4 "Future tense"]
             ]])
         ]))))

;; person3sg (case verb-type
;;             :iti   "ide"
;;             :mogči "može"
;;             :ova   
;;             :va
;;             :ji
;;             :je
;;             :but
;;             :ja
;;             :ga->že
;;             :ka->če
;;             :xa->še
;;             :e
;;             :i
;;             :a
;; v)
