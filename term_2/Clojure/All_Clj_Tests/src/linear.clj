(defn helper-apply [f] #(apply mapv f %&))
(defn helper-reduce [f] #(vec (reduce f %&)))
(defn matrix-multiply [f] (helper-reduce #(for [x, %1] (f x %2))))
(defn shapeless [f] (fn body [a b] (if (number? a) (f a b) (mapv body a b))))

(def s+ (shapeless +))
(def s- (shapeless -))
(def s* (shapeless *))

(def v+ s+)
(def v- s-)
(def v* s*)
(def v*s (helper-reduce #(for [x, %1] (* x %2))))

(defn scalar [& vs] (apply + (apply v* vs)))
(def vect (helper-reduce #(for [pair [[2, 1], [0, 2], [1, 0]]]
                            (- (* (nth %1 (second pair)) (nth %2 (first pair)))
                               (* (nth %1 (first pair)) (nth %2 (second pair)))))))

(defn transpose [m] (apply mapv vector m))

(def m+ (helper-apply v+))
(def m- (helper-apply v-))
(def m* (helper-apply v*))
(def m*s (matrix-multiply v*s))
(def m*v (matrix-multiply scalar))
(def m*m (helper-reduce #(vec (for [v1, %1] (vec (for [v2 (transpose %2)] (scalar v1 v2)))))))