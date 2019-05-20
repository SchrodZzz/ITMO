ins((IK, IV), nil, t((IK, IV), nil, nil)).
ins((IK, IV), t((XK, XV), L, R), t((YK, YV), P, Q)) :-
    (   IK < XK
    ->  ins((IK, IV), L, U),
        (P, (YK, YV), Q) = (U, (XK, XR), R)
    ;   IK > XK
    ->  ins((IK, IV), R, U),
        (P, (YK, YV), Q) = (L, (XK, XV), U)
    ;   (P, (YK, YV), Q) = (L, (XK, XV), R)
    ).

lookup((K, V),t((K,V),_,_)).
lookup((K, V),t((VK,VV),L,_)) :- K < VK, lookup((K,V),L).
lookup((K, V),t((VK, VV),_,R)) :- K > VK, lookup((K, V),R).

inl([], T, T).
inl([N|Ns], T0, T) :-
    ins(N, T0, T1),
    inl(Ns, T1, T).


tree_build(L,R) :-
        inl(L,nil,R).

map_get(T,K,V) :-
        lookup((K,V),T).