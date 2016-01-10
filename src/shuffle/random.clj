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
  ([i v] (lazy-seq (cons (next i v) (random-numbers (inc i) v)))))

(defn next
  "Returns a new pseudorandom number that's probably around int size. No promises."
  [i val]
  (if (>= 624 i)
      ;;if the index is >= 624, refresh the seed value vectors
      (twist)
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
        (lower-32-bits num))))
        ;;wait, it's all magic. Damn.
        ;;--END MAGIC--

(defn- twist
  "Refreshes the vector of seed values."
  []

  )

 def twist(self):
     for i in range(0, 624):
         # Get the most significant bit and add it to the less significant
         # bits of the next number
         y = _int32((self.mt[i] & 0x80000000) +
                    (self.mt[(i + 1) % 624] & 0x7fffffff))
         self.mt[i] = self.mt[(i + 397) % 624] ^ y >> 1

         if y % 2 != 0:
             self.mt[i] = self.mt[i] ^ 0x9908b0df
     self.index = 0
















     (def twister-state (agent :validator validator-fn
                               :error-handler handler-fn
                               :error-mode :continue))


     ;validate-fn must be nil or a side-effect-free fn of one
     ;argument, which will be passed the intended new state on any state
     ;change. If the new state is unacceptable, the validate-fn should
     ;return false or throw an exception.
     (defn validator-fn
       "Gatekeeper for new state; returns false if the new state is unacceptible."
       [new-state] ;;that is, if it isn't (a-num [vector length 624])
       (and (number? (first new-state))
           (vector? (second new-state))
           (= 624 (count (second new-state)))))

     ;;andler-fn is called if an action throws an exception or
     ;;f validate-fn rejects a new state
     (defn handler-fn ""
       [ex ag]
       (print (str "An error occurred! Agent " ag " was hit!\nThe cause of the error was: " ex)))
