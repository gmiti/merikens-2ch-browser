(ns merikens-2ch-browser.routes.mobile.home
  (:use compojure.core)
  (:require [clojure.string :refer [split trim]]
            [clojure.stacktrace :refer [print-stack-trace]]
            [clojure.data.json :as json]
            [ring.handler.dump]
            [ring.util.response :as response]
            [ring.util.codec :refer [url-encode url-decode]]
            [compojure.core :refer :all]
            [noir.response :refer [redirect]]
            [noir.request]
            [noir.session :as session]
            [noir.validation :refer [rule errors? has-value? on-error]]
            [hiccup.core :refer [html]]
            [hiccup.page :refer [include-css include-js]]
            [hiccup.form :refer :all]
            [hiccup.element :refer :all]
            [hiccup.util :refer [escape-html]]
            [taoensso.timbre :as timbre]
            [clj-http.client :as client]
            [clj-time.core]
            [clj-time.coerce]
            [clj-time.format]
            [merikens-2ch-browser.layout :as layout]
            [merikens-2ch-browser.util :refer :all]
            [merikens-2ch-browser.param :refer :all]
            [merikens-2ch-browser.url :refer :all]
            [merikens-2ch-browser.auth :refer :all]
            [merikens-2ch-browser.db.core :as db]
            [com.climate.claypoole :as cp]))



(defn mobile-home-page []
  (if (check-login)
    (redirect "/mobile-main")
    (layout/mobile-public
      [:div {:data-role "page" :data-dom-cache "true" :data-title "はじめに"}
       [:div {:role "main" :class "ui-content"}
        (escape-html app-name) "にようこそ! このモバイル版は現在開発中です。"
        (link-to {:data-role "button" :class "ui-btn ui-shadow ui-corner-all ui-btn-icon-left" :data-transition "fade"} "/mobile-login" "ログイン")]])))



(defn mobile-main-page []
  (layout/mobile-login-required
    ; [:div {:data-role "header" :data-theme "a"}　[:h1 "目次"]]
    ; [:script "$(function(){ $(\"[data-role='header'], [data-role='footer']\").toolbar(); });"]
    [:div {:data-role "page" :data-dom-cache "true" :data-title "目次"}
     [:div {:role "main" :class "ui-content"}
      [:ul {:data-role "listview"}
       [:li (link-to "/mobile-favorite-boards" "お気に板")]
       [:li (link-to "/mobile-favorite-threads" "お気にスレ")]
       [:li (link-to "/mobile-recently-viewed-threads" "最近読んだスレ")]
       [:li (link-to "/mobile-recently-posted-threads" "書込履歴")]
       ; [:li (link-to "/mobile-2ch-sc" "板一覧(2ch.sc)")]
       ; [:li (link-to "/mobile-2ch-net" "板一覧(2ch.net)")]
       ; [:li (link-to "/mobile-open2ch-net" "板一覧(open2ch.net)")]
       ; [:li (link-to "/mobile-server-info" "サーバー情報")]
       ; [:li (link-to "/mobile-image-downloads" "自動画像ダウンロード")]
       [:li (link-to {:data-transition "fade"} "/mobile-logout" "ログアウト")]]]]))



(defroutes mobile-home-routes
  (GET "/m" [] (redirect "/mobile"))
  (GET "/mobile" [] (mobile-home-page))
  (GET "/mobile-main" [] (mobile-main-page)))

