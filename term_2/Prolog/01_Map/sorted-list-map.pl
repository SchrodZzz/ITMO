map_get([(Key, Value) | _], Key, Value).
map_get([(Key1, _) | T], Key, Value) :- Key > Key1, map_get(T, Key, Value).

map_remove([(Key, _) | T], Key, T) :- !.
map_remove([(Key1, Value1) | T], Key, [(Key1, Value1) | T1]) :- Key > Key1, map_remove(T, Key, T1), !.
map_remove(M, _, M).

map_put([], Key, Value, [(Key, Value)]) :- !.
map_put([(Key, _) | T], Key, Value, [(Key, Value) | T]) :- !.
map_put([(Key1, Value1) | T], Key, Value, [(Key1, Value1) | T1]) :- Key > Key1, map_put(T, Key, Value, T1), !.
map_put(T, Key, Value, [(Key, Value) | T]).

map_replace([(Key, _) | T], Key, Value, [(Key, Value) | T]) :- !.
map_replace([(Key1, Value1) | T], Key, Value, [(Key1, Value1)| T1]) :- Key1 < Key, map_replace(T, Key, Value, T1), !.
map_replace(M, _, _, M).