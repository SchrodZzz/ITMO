#!/bin/bash

while true
do
    read s
    echo "$s" >> task5.tmp

    if [[ "$s" == "QUIT" ]]
    then
        exit 0
    fi

    if [[ ! "$s" =~ [0-9]+ && "$s" != "+" && "$s" != "*" ]]
    then
        echo "Stopped by incorrect message"
        exit 1
    fi
done
