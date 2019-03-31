(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (repeatedly 10 
      (is 
        (= 
          :p1-win 
          (play-round 
            [(rand-nth suits) :ace] 
            [(rand-nth suits) (rand-nth (butlast ranks))])))
      )
    )
  (testing "queens are higher rank than jacks"
    (is (= :p1-win (play-round
                   [(rand-nth suits) :queen] [(rand-nth suits) :jack])))
    (is (= :p2-win (play-round
                   [(rand-nth suits) :jack] [(rand-nth suits) :queen])))
    )
  (testing "kings are higher rank than queens"
    (is (= :p1-win (play-round
                   [(rand-nth suits) :king] [(rand-nth suits) :queen])))
    (is (= :p2-win (play-round
                   [(rand-nth suits) :queen] [(rand-nth suits) :king])))
    )
  (testing "aces are higher rank than kings"
    (is (= :p1-win (play-round
                   [(rand-nth suits) :ace] [(rand-nth suits) :king])))
    (is (= :p2-win (play-round
                   [(rand-nth suits) :king] [(rand-nth suits) :ace])))
    )
  (testing "if the ranks are equal, clubs beat spades"
    (let
      [same-rank (rand-nth ranks)]
        (is (= :p1-win (play-round
                       [:club same-rank] [:spade same-rank])))
        (is (= :p2-win (play-round
                       [:spade same-rank] [:club same-rank])))
    ))
  (testing "if the ranks are equal, diamonds beat clubs"
    (let
      [same-rank (rand-nth ranks)]
        (is (= :p1-win (play-round
                       [:diamond same-rank] [:club same-rank])))
        (is (= :p2-win (play-round
                       [:club same-rank] [:diamond same-rank])))
    ))
  (testing "if the ranks are equal, hearts beat diamonds"
    (let
      [same-rank (rand-nth ranks)]
        (is (= :p1-win (play-round
                       [:heart same-rank] [:diamond same-rank])))
        (is (= :p2-win (play-round
                       [:diamond same-rank] [:heart same-rank])))
    )))

(deftest test-play-game
  (testing "the player loses when they run out of cards"
    (is (= :p1-win (play-game cards [])))
    (is (= :p2-win (play-game [] cards)))
    )
  )


