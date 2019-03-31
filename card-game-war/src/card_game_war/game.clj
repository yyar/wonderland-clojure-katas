(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(def suit-point 
  {:spade 1 :club 2 :diamond 3 :heart 4})

(def rank-point 
  (reduce 
    #(into % {%2 %2}) 
    {:jack 11 :queen 12 :king 13 :ace 14}
    (filter number? ranks)
    ))

(def p1-cards '([:heart :jack] [:club 9]))
(def p2-cards '([:club 3] [:club :ace]))

(defn play-round [player1-card player2-card]
  (let [p1-suit (suit-point (first player1-card))
        p1-rank (rank-point (second player1-card))
        p2-suit (suit-point (first player2-card))
        p2-rank (rank-point (second player2-card))]
    (cond
      (> p1-rank p2-rank) :p1-win ;p1 win
      (< p1-rank p2-rank) :p2-win ;p2 win
      :else
      (cond
        (> p1-suit p2-suit) :p1-win ;p1 win
        (< p1-suit p2-suit) :p2-win ;p2 win
        :else :draw
        )
      )
    ))

(defn play-game [player1-cards player2-cards]
  (loop 
    [p1-cards player1-cards
     p2-cards player2-cards]
    (if (empty? p1-cards)
      :p2-win
      (if (empty? p2-cards)
        :p1-win
          (let 
            [p1-card (first p1-cards)
             p2-card (first p2-cards)
             p1-r-cards (into [] (rest p1-cards))
             p2-r-cards (into [] (rest p2-cards))]
            (case (play-round p1-card p2-card)
              :p1-win (recur (concat p1-r-cards (shuffle (vector p1-card p2-card))) p2-r-cards)
              :p2-win (recur p1-r-cards (concat p2-r-cards (shuffle (vector p1-card p2-card))))
              (recur p1-r-cards p2-r-cards)
            ))
          ))))
