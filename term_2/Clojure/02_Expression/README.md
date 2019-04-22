*   _Базовая_
    *   Код должен находиться в файле `expression.clj`.
    *   [Исходный код тестов](/git/geo/paradigms-2019/src/master/clojure/cljtest/functional/ClojureFunctionalExpressionTest.java)
        *   Запускать c аргументом `easy` или `hard`
*   _Модификация_. Дополнительно реализовать поддержку:
    *   унарных операций:
        *   `square` (`square`) — возведение в квадрат, `(square 3)` равно 9;
        *   `sqrt` (`sqrt`) — извлечение квадратного корня из модуля аргумента, `(sqrt -9)` равно 3.
    *   [Исходный код тестов](/git/geo/paradigms-2019/src/master/clojure/cljtest/functional/ClojureFunctionalSquareSqrtTest.java)
*   _Модификация_. Дополнительно реализовать поддержку:
    *   операций:
        *   `min` (`min`) — минимум, `(min 1 2 6)` равно 1;
        *   `max` (`max`) — максимум, `(min 1 2 6)` равно 6;
    *   [Исходный код тестов](/git/geo/paradigms-2019/src/master/clojure/cljtest/functional/ClojureFunctionalMinMaxTest.java)
        *   Запускать c аргументом `easy` или `hard`
