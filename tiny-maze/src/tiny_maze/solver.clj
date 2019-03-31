(ns tiny-maze.solver)

(def test-maze
  [[:S 0 1]
   [1  0 1]
   [1  0 :E]])

(defn mv [maze x y]
  (nth (nth maze y) x))

(defn can-go? [maze x y]
  (and 
    (>= x 0)
    (>= y 0)
    (< x (count (first maze)))
    (< y (count maze)) 
    (or 
      (= 0 (mv maze x y))
      (= :E (mv maze x y)))
    )
  )

(defn w [x y] [(- x 1) y]) 

(defn e [x y] [(+ x 1) y]) 

(defn n [x y] [x (- y 1)]) 

(defn s [x y] [x (+ y 1)]) 

(def directions [ n e w s ])

(defn point-maze [maze x y]
  (update maze y #(assoc % x :x))
  )

(defn arrived? [maze]
  (empty? (filter #(= % :E) (flatten maze))))

(defn solve-maze 
  ([maze] (solve-maze maze 0 0))
  ([maze x y] 
   (let
     [new-maze (point-maze maze x y)]
     (if (arrived? new-maze)
       new-maze
       (let 
         [direc (map #(% x y) directions)
          can-go-direc (filter #(can-go? new-maze (first %) (second %)) direc )]
         (if (empty? can-go-direc)
          new-maze 
          (first (filter arrived? (map 
            #(solve-maze new-maze (first %) (second %)) 
            can-go-direc)))
          )
         )
         )
       )
     )
  )

