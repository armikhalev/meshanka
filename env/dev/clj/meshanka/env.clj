(ns meshanka.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [meshanka.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[meshanka started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[meshanka has shut down successfully]=-"))
   :middleware wrap-dev})
