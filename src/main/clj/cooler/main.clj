(ns cooler.main
  "The cooler webapp.

  Plumbs together the ring routes into a runnable server."
  {:authors ["Reid \"arrdem\" McKenzie <me@arrdem.com>"]
   :license "https://www.eclipse.org/legal/epl-v10.html"}
  (:require [clojure.java.jdbc :as sql]
            [cheshire.core :as json]
            [compojure.core :refer [defroutes GET]]
            [ring.adapter.jetty :as ring]

            [cooler.routes :as cooler]))

(defroutes routes
  cooler/routes
  (route/resources "/")
  (route/not-found
   (layout/four-oh-four)))

(def application (wrap-defaults routes site-defaults))

(defn start [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn -main []
  (schema/migrate)
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
