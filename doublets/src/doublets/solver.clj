(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.set :as s]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))


(defn relevant? [word w1]
  (and (not= word w1) 
       (= (count word) (count w1))
       (= (count (filter true? (map = word w1)))
          (- (count word) 1))
       ))

(defn relevant 
  ([word]
   (filter #(relevant? word %) words))
  ([word filterlist]
   (filter #(relevant? word %) (s/difference (set words) (set filterlist))))
  )

(defstruct node :key :order)
(defn doublets [word1 word2]
  (let [result (first (filter #(= (% :key) word2)
                              (tree-seq #(not= (% :key) word2)
                                        (fn [x] 
                                          (map #(struct-map node 
                                                            :key % 
                                                            :order (conj (x :order) (x :key)))
                                               (relevant (x :key) (x :order))
                                               ))
                                        {:key word1 :order []})))]
    (if (empty? result)
      []
      (conj (result :order) (result :key)))
    )
  )
