(ns fox-goose-bag-of-corn.puzzle)

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])

(defn is-here? [l e]
  (>= (.indexOf l e) 0))

(defn is-last-pos? [[f s t]]
  (empty? f))

(defn is-possible? [l]
  (or (is-here? l :you)
      (not (or 
        (and (is-here? l :fox) (is-here? l :goose))
        (and (is-here? l :corn) (is-here? l :goose))
        ))
      ))

; need to add something
(defn filter-carry-or-not [cand m]
  (let [z (map #(conj [(into [] %)] %2) 
             (map #(remove (fn [x] (= x %)) cand) cand) cand)]
    (do (println nil)
    (filter (fn [x] 
              (let [[r c] x] 
                (is-possible? r))) z))
    ))


(defn next-first-to-third [f s t]
  (let [cand (butlast f)
        z (filter-carry-or-not cand t)
        [r c] (first z)]
      (do (println nil)
        [[r (conj s c :you) t] [r s (conj t c :you)]]
         )))

(defn next-third-to-first [f s t]
  (let [cand (butlast t)
        z (filter-carry-or-not (conj cand []) f)
        [r c] (first z)]
      (do (println nil)
          (if (= c []) 
              [[f (conj s :you) r] [(conj f :you) s r]]
              (let [r2 (into [] (remove #(= [] %) r))]
              [[f (conj s c :you) r2] [(conj f c :you) s r2]]))
         )))


(defn next-step [lll]
  (do (println (last lll))
  (let [[f s t] (last lll)]
    (if (is-here? f :you)
      (do (println nil) (into [] (concat lll (next-first-to-third f s t))))
      (do (println nil) (into [] (concat lll (next-third-to-first f s t)))))))
      )


(defn iter-step [l]
  (loop [lll l]
    (if (is-last-pos? (last lll))
      lll
      (recur (next-step lll)))
      ))

(defn river-crossing-plan []
  (iter-step start-pos))
