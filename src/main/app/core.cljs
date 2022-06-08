(ns main.app.core
  (:require [fipp.edn :as fedn]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [reitit.coercion.spec :as rss]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            ["react-bootstrap" :as bs]
            [views.home :as home]
            [views.aboutme :as aboutme]
            [views.projects :as projects]))

(defn home-page []
  [:div
   [:h2 "Home"]
   [:p "Hello World!"]])

(defn about-page []
  [:div
   [:h2 "About"]
   [:p "This is the About Page!"]])

(defn projects-page []
  [:div
   [:h2 "Projects"]
   [:p "Check out my Projects!"]])

(def routes
  [["/"
    {:name ::home
     :view home-page}]

   ["/about"
    {:name ::about
     :view about-page
     }]
   
   ["/projects"
    {:name ::projects
     :view projects-page}]])

(defonce current-match (r/atom nil))

(defn current-page []
  [:div
   [:> bs/Navbar {:collapseOnSelect true :expand "lg" :bg "dark" :variant "dark"}
    [:> bs/Container
     [:> bs/Navbar.Brand {:href (rfe/href ::home)} "Dadcast"]
     [:> bs/Navbar.Toggle {:aria-controls "responsive-navbar-nav"}]
     [:> bs/Navbar.Collapse {:id "responsive-navbar-nav"}
     [:> bs/Nav {:class "me-auto"}
      [:> bs/Nav.Link {:href (rfe/href ::home)} "Home"]
      [:> bs/Nav.Link {:href (rfe/href ::about)} "About Me"]
      [:> bs/Nav.Link {:href (rfe/href ::projects)} "Projects"]]]]]
   (if @current-match
     (let [view (-> @current-match :data :view)]
       [view @current-match]))])

(defn init! []
  (rfe/start!
    (rf/router routes {:data {:coercion rss/coercion}})
    (fn [m] (reset! current-match m))
    ;; set to false to enable HistoryAPI
    {:use-fragment true})
  (rdom/render [current-page] (.getElementById js/document "app")))

(init!)
