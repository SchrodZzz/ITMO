1.  Добавьте в программу вычисляющую выражения обработку ошибок, в том числе:
    *   ошибки разбора выражений;
    *   ошибки вычисления выражений.
2.  Для выражения `1000000*x*x*x*x*x/(x-1)` вывод программы должен иметь следующий вид: 
#                 
                    x       f
                    0       0
                    1       division by zero
                    2       32000000
                    3       121500000
                    4       341333333
                    5       overflow
                    6       overflow
                    7       overflow
                    8       overflow
                    9       overflow
                    10      overflow
                    
 Результат `division by zero` (`overflow`) означает, что в процессе вычисления произошло деление на ноль (переполнение).
3.  При выполнении задания следует обратить внимание на дизайн и обработку исключений.
4.  Человеко-читаемые сообщения об ошибках должны выводится на консоль.
5.  Программа не должна «вылетать» с исключениями (как стандартными, так и добавленными).

#### Модификации
*   _Базовая_
    *   Класс `ExpressionParser` должен реализовывать интерфейс [Parser](/git/geo/paradigms-2019/src/master/java/expression/exceptions/Parser.java)
    *   Классы `CheckedAdd`, `CheckedSubtract`, `CheckedMultiply`, `CheckedDivide` и `CheckedNegate` должны реализовывать интерфейс [TripleExpression](/git/geo/paradigms-2019/src/master/java/expression/TripleExpression.java)
    *   Нельзя использовать типы `long` и `double`
    *   Нельзя использовать методы классов `Math` и `StrictMath`
    *   [Исходный код тестов](/git/geo/paradigms-2019/src/master/java/expression/exceptions/ExceptionsTest.java)
*   _HighLow_
    *   Дополнительно реализовать унарные операции:
        *   `high` — значение, у которого оставлен только самый старший установленный бит `high -4` равно `Integer.MIN_VALUE`;
        *   `low` — значение, у которого оставлен только самый младший установленный бит `low 18` равно `2`.
    *   [Исходный код тестов](/git/geo/paradigms-2019/src/master/java/expression/exceptions/ExceptionsHighLowTest.java)
