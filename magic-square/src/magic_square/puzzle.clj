(ns magic-square.puzzle)

(def values [1.0 1.5 2.0 2.5 3.0 3.5 4.0 4.5 5.0])

(defn magic-square-3by3 [values]
  [[(nth values 5) (nth values 0) (nth values 7)]
   [(nth values 6) (nth values 4) (nth values 2)]
   [(nth values 1) (nth values 8) (nth values 3)]]
  )

(defn magic-square [values]
  (magic-square-3by3 (sort values)))
