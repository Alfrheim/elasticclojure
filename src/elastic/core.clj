(ns elastic.core 
  (:require [clj-http.client :as client])
  (:gen-class))

(def ^:const urlElastic
  "http://url/_search?pretty")

(defn consulta
  [& args]
  (client/get urlElastic"))

(defn consultaPost
  [& args]
  ((client/post urlElastic {:body "{
  \"query\"  : {
    \"filtered\" : {
      \"filter\": {
        \"range\": {
          \"_timestamp\": {
            \"gte\" : \"now-10d\"
          }
        }
      }
    }
  },
  \"size\" : 0,
  \"sort\": [
    {\"_timestamp\": {\"order\": \"asc\"}}
  ],
  \"fields\" : [\"_timestamp\"]
}"}) :body))

(defn esPost [post]
  [post post]
  (client/post urlElastic {:body post}))

(defn check-total-documents []
  (while true
    (do
      (println (consultaPost))
      (Thread/sleep 5000))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
                                        ;(print "query=> ")
                                        ; (let  [string (read-line)]
                                        ;  (esPost string))
  (while true
    (do
      (println (str (apply str "\033[32m" "json" "\033[36m" "=> " "\033[0m)")))
      (println (esPost (read-line)))
      )
    )
  (println "Hello, World!"))
