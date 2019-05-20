1.  Реализуйте ассоциативный массив (map).
2.  **Простой вариант.** Ассоциативный массив на упорядоченном списке пар ключ-значение.

    Разработайте правила:

    *   `map_get(ListMap, Key, Value)`, проверяющее, что дерево содержит заданную пару ключ-значение.
    *   `map_put(ListMap, Key, Value, Result)`, добавляющее пару ключ-значение в дерево, или заменяющее текущее значение для ключа;
    *   `map_remove(ListMap, Key, Result)`, удаляющее отображение для ключа.

    Правила не должны анализировать хвост списка, если в нем точно нет необходимого ключа.

3.  **Сложный вариант.** Ассоциативный массив на двоичном дереве.

    Разработайте правила:

    *   `tree_build(ListMap, TreeMap)`, строящее дерево из упорядоченного набора пар ключ-значение;
    *   `map_get(TreeMap, Key, Value)`.

    Для решения можно реализовать любое дерево поиска логарифмической высоты.

4.  **Бонусный вариант.** Дополнительно разработайте правила:

    *   `map_put(TreeMap, Key, Value, Result)`;
    *   `map_remove(TreeMap, Key, Result)`.

#### Модификации
*   _Простая_
    *   Код должен находиться в файле `sorted-list-map.pl`.
    *   [Исходный код тестов](/git/geo/paradigms-2019/src/master/prolog/prtest/list/PrologListTest.java)
        *   Запускать c аргументом `sorted`
*   _Сложная_
    *   Код должен находиться в файле `tree-map.pl`.
    *   [Исходный код тестов](/git/geo/paradigms-2019/src/master/prolog/prtest/tree/PrologTreeTest.java)
        *   Запускать c аргументом `hard` или `bonus`
*   _Replace_
    *   Добавьте правило `map_replace(Map, Key, Value, Result)`, заменяющего значения ключа на указанное, если ключ присутствует.
    *   Исходный код тестов: [простые](/git/geo/paradigms-2019/src/master/prolog/prtest/list/PrologListReplaceTest.java), [сложные](/git/geo/paradigms-2019/src/master/prolog/prtest/tree/PrologTreeReplaceTest.java)
