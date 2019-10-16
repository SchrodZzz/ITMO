#!/bin/bash

while true
do
    read s
    case $s in
        TERM)
            kill -SIGTERM $(cat task6.tmp)
            exit 0
            ;;
        *)
            continue
            ;;
    esac
done
