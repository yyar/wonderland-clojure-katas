(ns wonderland-number.finder)

(def rg (range 100000 1000000))

(defn sameDigits? [n1 n2]
  (let [s1 (set (str n1))
        s2 (set (str n2))]
       (= s1 s2)))

(defn isWonderlandNumber? [n1]
  (reduce #(and % %2) (map #(sameDigits? n1 (* % n1)) [2 3 4 5 6])))

(defn wonderland-number []
  ;; calculate me
  (first (filter isWonderlandNumber? rg)))
