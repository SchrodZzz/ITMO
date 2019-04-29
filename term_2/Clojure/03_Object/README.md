1.  Разработайте конструкторы `Constant`, `Variable`, `Add`, `Subtract`, `Multiply` и `Divide` для представления выражений с одной переменной.
    1.  Пример описания выражения `2x-3`:

            (def expr
                (Subtract
                    (Multiply
                      (Constant 2)
                       (Variable "x"))
                    (Const 3)))


    2.  Функция `(evaluate expression vars)` должна производить вычисление выражения `expression` для значений переменных, заданных отображением `vars`. Например, `(evaluate expr {"x" 2})` должно быть равно 1.
    3.  Функция `(toString expression)` должна выдавать запись выражения в стандартной для Clojure форме.
    4.  Функция `(parseObject "expression")` должна разбирать выражения, записанные в стандартной для Clojure форме. Например,
         `(parseObject "(- (* 2 x) 3)")`
        должно быть эквивалентно `expr`.
    5.  Функция `(diff expression "variable")` должена возвращать выражение, представляющее производную исходного выражения по заданой пермененной. Например, `(diff expression "x")` должен возвращать выражение, эквивалентное `(Constant 2)`, при этом выражения `(Subtract (Const 2) (Const 0))` и

            (Subtract
              (Add
                (Multiply (Const 0) (Variable "x"))
                (Multiply (Const 2) (Const 1)))
              (Const 0))


        так же будут считаться правильным ответом.
2.  **Усложненный вариант.** Констуркторы `Add`, `Subtract`, `Multiply` и `Divide` должны принимать произвольное число аргументов. Разборщик так же должен допускать произвольное число аргументов для `+`, `-`, `*`, `/`.
3.  **Бонусный вариант.** Функция `(parseObjectInfix "expression")` должна разбирать выражения, записанные в инфиксной форме. Например,
    `(parseObjectInfix "2 * x - 3")`
    должно быть эквивалентно `expr`. __(вариант был отменен)__
4.  При выполнение задания можно использовать любой способ преставления объектов.

#### Модификации
*   _Базовая_
    *   Код должен находиться в файле `expression.clj`.
    *   [Исходный код тестов](/git/geo/paradigms-2019/src/master/clojure/cljtest/object/ClojureObjectExpressionTest.java)
        *   Запускать c аргументом `easy` или `hard`
*   _Модификация SquareSqrt_. Дополнительно реализовать поддержку:
    *   унарных операций:
        *   `Square` (`square`) — возведение в квадрат, `(square 3)` равно 9;
        *   `Sqrt` (`sqrt`) — извлечение квадратного корня из модуля аргумента, `(sqrt -9)` равно 3.
    *   [Исходный код тестов](/git/geo/paradigms-2019/src/master/clojure/cljtest/object/ClojureObjectSquareSqrtTest.java)
