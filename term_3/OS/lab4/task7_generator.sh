#!/bin/bash

while true
do
    read s
    case $s in
        "+")
            kill -USR1 $(cat task7.tmp)
            ;;
        "*")
            kill -USR2 $(cat task7.tmp)
            ;;
        TERM)
            kill -SIGTERM $(cat task7.tmp)
            exit 0
            ;;
        *)
            continue
            ;;
    esac
done
