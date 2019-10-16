#!/bin/bash

echo $$ > task6.tmp

count=0
mode="work"

handle()
{
	mode="stop"
}

trap 'handle' SIGTERM

while true
do
    case $mode in
        "work")
            count=$(($count + 1))
            echo $count
            ;;
        "stop")
            echo "Stopped by SIGTERM signal"
            rm task6.tmp
            exit 0
            ;;
    esac
    sleep 1
done
