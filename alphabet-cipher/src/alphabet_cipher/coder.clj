(ns alphabet-cipher.coder)
(def default-row "abcdefghijklmnopqrstuvwxyz")

(defn nth-row [n]
  (concat (drop n default-row) (take n default-row))
  )

(defn alphabet-index 
  ([c] (- (int c) (int \a)))
  ([c fc] (mod (- (int c) (int fc)) (count default-row)))
  )

(defn alphabet-in-index [i] (char (+ i (int \a))))

(defn encode [keyword message]
  (apply str 
         (map #(nth (nth-row (alphabet-index %)) (alphabet-index %2)) 
              (take (count message) (apply concat (repeat keyword))) message
              )
         )
  )

(defn decode [keyword message]
  (apply str (map #(alphabet-in-index 
    (alphabet-index %2 (first (nth-row (alphabet-index %)))))
       (take (count message) (apply concat (repeat keyword))) message)
  ))

(defn is-repeated? [s l]
  (loop [s s l l]
    (let [cs (count s) cl (count l)]
      (if (> cs cl) 
          (= (take cl s) l)
          (and 
            (= s (apply str (take cs l)))
            (recur s (drop cs l)) 
            ))
      )))

(defn decipher [cipher message]
  (let [r (decode message cipher)]
    (first (filter #(is-repeated? % r) 
                   (map #(apply str (take % r)) (range 1 (count r)))))
  ))

