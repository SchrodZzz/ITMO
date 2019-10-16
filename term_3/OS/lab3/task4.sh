#!/bin/bash

outFile="log/task4.log"

res=""
for pid in $(ps -A -o pid | tail +2); do
    dif="$(awk '{ print $2 - $3 }' /proc/$pid/statm)"
    if [ "$dif" ]; then
        res="$res$pid $dif\n"
    fi
done

printf "$res" | sort --reverse --numeric-sort --key=2 | awk '{print $1 " : " $2}' > $outFile
