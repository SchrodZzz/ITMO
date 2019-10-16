#!/bin/bash

./task4_inf.sh&pid1=$!
./task4_inf.sh&pid2=$!

echo $pid1 $pid2
renice +10 $pid1

sleep 3
ps ax -o pid,pcpu | grep "$pid1\|$pid2"

kill $pid1
kill $pid2
