#_-----------------------------------HW9-----------------------------------_#
(defn get-prototype [obj key]                               ;infinite loop fixed
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (get-prototype (obj :prototype) key)))


(defn method [key]
  (fn [this & args] (apply (get-prototype this key) this args)))


(def toStringSuffix (method :toStringSuffix))
(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff))

(defn Constant [x]
  {
   :evaluate       (constantly x)
   :toString       (constantly (format "%.1f" (double x)))
   :toStringSuffix toString
   :diff           (fn [_ _] (Constant 0))
   })

(defn Variable [x]
  {
   :evaluate       (fn [_ vars] (get vars (str (first (.toLowerCase x)))))
   :toString       (constantly (str x))
   :toStringSuffix toString
   :diff           (fn [_ v] (if (= v x) (Constant 1) (Constant 0)))
   })


(defn field [key]
  (fn [name] (get-prototype name key)))

(def diff-helper (field :diff-helper))
(def operands (field :operands))
(def action (field :action))
(def sign (field :sign))

(def Abstract-Op                                            ;copy-paste fixed (removed this, diff e.t.c)
  {
   :evaluate       (fn [this vars] (apply (action this) (mapv (fn [operand] (evaluate operand vars)) (operands this))))
   :toString       (fn [this] (str "(" (sign this) (apply str (mapv #(str " " (toString %)) (operands this))) ")"))
   :toStringSuffix (fn [this] (str "(" (apply str (mapv #(str (toStringSuffix %) " ") (operands this))) (sign this) ")"))
   :diff           (fn [this key]
                     (if (= (count (operands this)) 2)
                       ((diff-helper this) ((operands this) 0) ((operands this) 1)
                         (diff ((operands this) 0) key) (diff ((operands this) 1) key))
                       ((diff-helper this) ((operands this) 0) (diff ((operands this) 0) key))))
   })

(defn Op [sign action diff-helper]
  (let [base {:prototype   Abstract-Op
              :sign        sign
              :action      action
              :diff-helper diff-helper}]
    (fn [& args]
      {:prototype base
       :operands  (vec args)
       })))


(def Add (Op "+" + (fn [_ _ da db] (Add da db))))

(def Subtract (Op "-" - (fn [_ _ da db] (Subtract da db))))

(def Multiply (Op "*" * (fn [a b da db] (Add                ;identical case fixed
                                          (Multiply da b)
                                          (Multiply db a)))))

(def Divide (Op "/" #(/ %1 ^double %2) (fn [a b da db] (Divide
                                                         (Subtract
                                                           (Multiply b da)
                                                           (Multiply a db))
                                                         (Multiply b b)))))

(def Negate (Op "negate" - (fn [_ da] (Subtract da))))


(def TWO (Constant 2))

(def Square (Op "square" #(* % %) (fn [a da] (Multiply (Multiply TWO a) da))))

(def Sqrt (Op "sqrt" #(Math/sqrt (Math/abs ^double %)) (fn [a da] (Divide
                                                                    (Multiply da a)
                                                                    (Multiply TWO (Sqrt (Multiply (Square a) a)))))))

(def OPERATORS
  {'+      Add
   '-      Subtract
   '*      Multiply
   '/      Divide
   'negate Negate
   'square  Square
   'sqrt    Sqrt
   })

(defn parseObjSeq [expr]
  (cond
    (number? expr) (Constant expr)
    (symbol? expr) (Variable (str expr))
    (seq? expr) (apply (get OPERATORS (first expr)) (mapv parseObjSeq (rest expr)))))

(defn parseObject [expr] (parseObjSeq (read-string expr)))


#_-----------------------------------HW10-----------------------------------_#
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)

(defn _empty [value] (partial -return value))
(defn _char [p] (fn [[c & cs]] (if (and c (p c)) (-return c cs))))
(defn _map [f] (fn [result] (if (-valid? result) (-return (f (-value result)) (-tail result)))))
(defn _combine [f a b] (fn [str] (let [ar ((force a) str)]
                                   (if (-valid? ar) ((_map (partial f (-value ar))) ((force b) (-tail ar)))))))
(defn _either [a b] (fn [str] (let [ar ((force a) str)] (if (-valid? ar) ar ((force b) str)))))
(defn _parser [p] (fn [input] (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))

(defn +char [chars] (_char (set chars)))
(defn +char-not [chars] (_char (comp not (set chars))))
(defn +map [f parser] (comp (_map f) parser))
(defn iconj [coll value] (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps] (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))
(defn +or [p & ps] (reduce (partial _either) p ps))
(defn +opt [p] (+or p (_empty nil)))
(defn +star [p] (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))

(def +parser _parser)
(def +ignore (partial +map (constantly 'ignore)))


(def *all-chars (mapv char (range 32 128)))
(def *space (+char (apply str (filter #(Character/isWhitespace %) *all-chars))))
(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))
(def *digit (+char (apply str (filter #(Character/isDigit %) *all-chars))))

(def *ws (+ignore (+star *space)))

(def *const (+map (comp Constant read-string) (+str (+seq (+opt (+char "-")) (+str (+plus *digit)) (+char ".") *digit))))

(def *op-start (+or *letter (+char "+-*/")))
(def *identifier (+str (+seqf cons *ws *op-start (+star (+or *op-start *digit)))))
(def *symbol (+map (comp #(get OPERATORS % (Variable (str %))) symbol) *identifier))

(def *value)
(defn *seq [p] (+seqn 1 *ws (+char "(") (+opt (+seqf cons *ws p (+star (+seqn 0 *ws p)))) *ws (+char ")")))
(def *list (+map (fn [list] (apply (last list) (butlast list))) (*seq (delay *value))))
(def *value (+or *const *symbol *list))

(def parseObjectSuffix (+parser (+seqn 0 *ws *value *ws)))


#_tests_#

;(def expr (parseObjectSuffix "(x 2.0 +) sadf sdfs fs"))
;(println (evaluate expr {"x" 0}))

;(def exprrr (read-string "(10 5 +)"))
;(println (type (read-string "(10 5 +)")))
;(println (type "(10 5 +)"))

;(println (== (evaluate (parseObjectSuffix "( 2.0 (     x 3   +   )*        )") {"x" 0}) 6.0))
;(def exprr (parseObject "(/ (+ x 5) 2.0)"))
;(println (toStringSuffix exprr))
;(println (toString exprr))

;(def exprr (parseObjectSuffix "(-5.0 y *)"))
;(println (evaluate exprr {"y" 0}))

;(def kek (parseObjectSuffix "(x 2.0 +)"))
;(println (evaluate kek {"x" 0.0}))


;(def *suffix-expr)
;
;(defn parseObjectSuffix [expr]
;  (let
;    [*space (+char " \t\n\r")
;     *digit (+char "0123456789")
;     *ws (+ignore (+star *space))
;     *all-chars (mapv char (range 32 128))
;     *letter (+char (apply str (filter #(Character/isLetter %) *all-chars)))
;     *number (+map read-string (+str (+map flatten (+seq (+seqf cons (+opt (+char "+-")) (+plus *digit))
;                                                         (+opt (+seqf cons (+char ".") (+plus *digit)))))))
;     *identifier (+str (+seqf cons *letter (+star (+or *letter *digit))))
;     *constant (+map Constant (+seqn 0 *ws *number))
;     *variable (+map Variable (+seqn 0 *ws (+map str (+char "xyz"))))
;     *negate (+seqf (constantly "negate") (+char "n") (+char "e") (+char "g") (+char "a") (+char "t") (+char "e'"))
;     *op (+or (+map str (+char "+-*/")) *negate)]
;    (letfn [(*suffix-sequence [p]
;              (+seqn 1 *ws (+char "(")
;                     (+seqf conj (+opt (+seqf cons *ws p (+star (+seqf conj *ws p)))) *ws *op) *ws (+char ")")))
;            (*brackets [] (+map (fn [list] (apply (OPERATORS (first list)) (rest list))) (*suffix-sequence #'*suffix-expr)))]
;      (let [*suffix-expr (+or *constant *variable (*brackets))])
;      (-value (*suffix-expr expr)))))
