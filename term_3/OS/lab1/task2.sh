#!/bin/bash

if [[ $# -ne 3 ]]; then
    echo -e "Incorrect amount of arguments ::\n\tProvide only 3 arguments"
else
    max=$1

    for n in "$@" #potential support of n arguments (-:
    do
        if [[ $n -gt $max ]]; then
            max=$n;
        fi;
    done

    echo "The greatest number is $max"
fi

#string can be greatest if other nums are negative
