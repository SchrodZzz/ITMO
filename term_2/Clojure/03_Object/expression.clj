(defn get-prototype [obj key]                               ;infinite loop fixed
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (get-prototype (obj :prototype) key)))


(defn method [key]
  (fn [this & args] (apply (get-prototype this key) this args)))

(def evaluate (method :evaluate))
(def toString (method :toString))
(def diff (method :diff))

(defn Constant [x]
  {
   :evaluate (constantly x)
   :toString (constantly (format "%.1f" (double x)))
   :diff     (fn [_ _] (Constant 0))                        ;TODO: fix this one ^-^
   })

(defn Variable [x]
  {
   :evaluate (fn [_ vars] (get vars x))
   :toString (constantly (str x))
   :diff     (fn [_ v] (if (= v x) (Constant 1) (Constant 0)))
   })



(defn field [key]
  (fn [name] (get-prototype name key)))

(def diff-helper (field :diff-helper))
(def operands (field :operands))
(def action (field :action))
(def sign (field :sign))

(def Abstract-Op                                            ;copy-paste fixed (removed this, diff e.t.c from op's)
  {
   :evaluate (fn [this vars] (apply (action this) (mapv (fn [operand] (evaluate operand vars)) (operands this))))
   :toString (fn [this] (str "(" (sign this) (apply str (mapv #(str " " (toString %)) (operands this))) ")"))
   :diff     (fn [this key]
               (if (= (count (operands this)) 2)
                 ((diff-helper this) ((operands this) 0) ((operands this) 1)
                   (diff ((operands this) 0) key) (diff ((operands this) 1) key))
                 ((diff-helper this) ((operands this) 0) (diff ((operands this) 0) key))))
   })

(defn Op [sign action diff-helper]
  (fn [& args]
    {:prototype   Abstract-Op
     :sign        sign
     :action      action
     :diff-helper diff-helper
     :operands  (vec args)
     }))


(def Add (Op "+" +
             (fn [_ _ da db] (Add da db))))

(def Subtract (Op "-" -
                  (fn [_ _ da db] (Subtract da db))))

(def Multiply (Op "*" *                                     ;identical case fixed
                  (fn [a b da db] (Add
                                    (Multiply da b)
                                    (Multiply db a)))))

(def Divide (Op "/" #(/ %1 ^double %2)
                (fn [a b da db] (Divide
                                  (Subtract
                                    (Multiply b da)
                                    (Multiply a db))
                                  (Multiply b b)))))

(def Negate (Op "negate" -
                (fn [_ da] (Subtract da))))


(def TWO (Constant 2))

(def Square (Op "square" #(* % %)
                (fn [a da] (Multiply (Multiply TWO a) da))))

(def Sqrt (Op "sqrt" #(Math/sqrt (Math/abs ^double %))
              (fn [a da] (Divide
                           (Multiply da a)
                           (Multiply TWO (Sqrt (Multiply (Square a) a)))))))

(def OPERATORS
  {'+      Add
   '-      Subtract
   '*      Multiply
   '/      Divide
   'negate Negate
   'square Square
   'sqrt   Sqrt
   })

(defn parseSeq [expr]
  (cond
    (number? expr) (Constant expr)
    (symbol? expr) (Variable (str expr))
    (seq? expr) (apply (get OPERATORS (first expr)) (mapv parseSeq (rest expr)))))

(defn parseObject [expr] (parseSeq (read-string expr)))


#_-----------------------------------BONUS-----------------------------------_#
;
;(def expr)
;(def ind (atom 0))
;(def token (atom ""))
;
;(defn whitespace? [s] (not (empty? (re-matches #"\s" (str s)))))
;
;(defn digit? [x] (not (empty? (re-matches #"\d" (str x)))))
;
;(defn variable? [x] (not (empty? (re-matches #"[xyz]" (str x)))))
;
;(defn retrieve [] (subs expr @ind (inc @ind)))
;
;(defn increment [] (swap! ind inc))
;
;(defn getIdentifier []
;  (if (or (= @ind (count expr)) (whitespace? (retrieve)))
;    ""
;    (do (increment) (str " " (getIdentifier)))))
;
;(defn getNumber
;  ([] (getNumber 0))
;  ([acc]
;   (if (or (= @ind (count expr)) (whitespace? (retrieve)) (not (digit? (retrieve))))
;     acc
;     (let [currInd @ind]
;       (do
;         (increment)
;         (getNumber (+ (* 10 acc) (- (int (get expr currInd)) 48))))))))
;
;(defn getVariable []
;  (let [res (retrieve)]
;    (do
;      (increment)
;      res)))
;
;(defn skipWhitespace []
;  (if (or (= @ind (count expr)) (not (whitespace? (retrieve))))
;    nil
;    (do (increment) (skipWhitespace))))
;
;(def TOKENS {"+" "ADD" "-" "SUB" "*" "MUL" "/" "DIV" "(" "LB" ")" "RB" "negate" "NEG"})
;(def PRIORITY {"NUM" 0 "VAR" 0 "LB" 0 "NEG" 0 "MUL" 1 "DIV" 1 "ADD" 2 "SUB" 2})
;
;(defn getToken []
;  (do
;    (skipWhitespace)
;    (cond
;      (= @ind (count expr)) nil
;      (contains? TOKENS (retrieve)) (do (reset! token (get TOKENS (retrieve))) (increment))
;      (digit? (retrieve)) (reset! token "NUM")
;      (variable? (retrieve)) (reset! token "VAR")
;      :else (do (reset! token (get TOKENS (getIdentifier))) (swap! ind #(+ % (count (getIdentifier))))))))
;
;(defn priorityParse
;  ([] (priorityParse 2 nil))
;  ([priority] (priorityParse priority nil))
;  ([priority res]
;   (cond
;     (= priority 2)
;     (cond
;       (= @token "ADD") (priorityParse 2 (Add res (priorityParse 1)))
;       (= @token "SUB") (priorityParse 2 (Subtract res (priorityParse 1)))
;       :else res)
;     (= priority 1)
;     (cond
;       (= @token "MUL") (priorityParse 1 (Multiply res (priorityParse 0)))
;       (= @token "DIV") (priorityParse 1 (Divide res (priorityParse 0)))
;       :else res)
;     :else
;     (do (getToken)
;         (cond
;           (= @token "NUM") (let [res (Constant (getNumber))] (do (getToken) res))
;           (= @token "VAR") (let [res (Variable (getVariable))] (do (getToken) res))
;           (= @token "LB") (let [res (priorityParse)] (do (getToken) res))
;           (= @token "NEG") (Negate (priorityParse 0)))))))
;
;(defn parseObjectInfix [expression]
;  (do (def expr expression) (priorityParse)))
;
;
#_tests_#

;(def expr (Add
;            (Variable "x")
;            (Sqrt
;              (Subtract
;                (Variable "y")
;                (Divide
;                  (Square
;                    (Constant 3.0))
;                  (Constant 4.0))))))
;(println (evaluate expr {"z" 0.0, "x" 0.0, "y" 0.0}))