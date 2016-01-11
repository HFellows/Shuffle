(ns shuffle.random)
;The idea is that I build my own PRNG - while many PRNGs are fast,
; my main concern is the quality of the output, and I don't trust Java
; I only need a few random numbers at a time. I'm planning on using
; promises or agents to generate a queue of them during free processor cycles
; instead of on-demand generation.
; See http://www0.cs.ucl.ac.uk/staff/d.jones/GoodPracticeRNG.pdf
;;Edit 6/1/16 IT'S GONNA BE PROMISES, LOL.

(def seed 56434081)
;TODO make it a function that reads from /dev/random on linux
;and figure out something else for other OS (OSes? OSs?)

(defn- lower-32-bits
  "Get 32 least significant bits."
  [x]
  (bit-and 0xFFFFFFFF x))

(defn- inital-numbers
  "A lazy sequence of what is essentially seed values. Moderately Magic."
  ([] (inital-numbers seed))
  ([n] (lazy-seq (cons n (inital-numbers
  ;;All constants are Magic, and should never be changed. Or discussed.
    (lower-32-bits (+ (* 1812433253 (bit-xor n (bit-shift-right n 30))) 1)))))))

(defn random-numbers
  "A lazy sequence of pseudorandom numbers created from a Mersenne Twister."
  ([] (random-numbers 0 (take 624 inital-numbers)) ;;624 is Magic. Don't change it.
  ([i v] (if (>= 624 i)
      ;;if the index is >= 624, refresh the seed value vectors
           (random-numbers 0 (twist val))
           (lazy-seq (cons (next i v) (random-numbers (inc i) v)))))))

(defn next
  "Returns a new pseudorandom number that's probably around int size. No promises."
  [i val]
      (as-> (nth i (second val)) num
        ;;--BEGIN MAGIC--
        (bit-xor num (bit-shift-right num 11))
          ;Right shift by 11
        (bit-xor num (bit-and 2636928640 (bit-shift-left num 7)))
          ;Left shift by 7, bitwise'd by 2636928640
        (bit-xor num (bit-and 4022730752 (bit-shift-left num 15)))
          ;Left shift by 15, bitwise'd by 4022730752
        (bit-xor num (bit-shift-right 18))
          ;Right shift by 15
        (lower-32-bits num)))
        ;;wait, it's all magic. Damn.
        ;;--END MAGIC--

(defn- twist
  "Refreshes the vector of seed values."
  [val]
  (loop [i 0 new []]
    (if (>= i 623)
      new
      (let
        [num (nth i val)
        ;--BEGIN MAGIC--
        y (lower-32-bits (+
            (bit-and (nth i val) 0x80000000)]
            (bit-and (% (nth (inc i) val) 624) 0x7fffffff)))
        result (if (odd? y) ;;Originally 'if y % 2 != 0'
          (bit-and (bit-shift-right (bit-xor (nth (% (+ i 397) 624) val) y) 1)
            0x7fffffff)))
          ((bit-shift-right (bit-xor (nth (% (+ i 397) 624) val) y) 1))]
      (recur (inc i) (conj new result)))))
