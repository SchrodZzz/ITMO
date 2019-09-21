#!/bin/bash

cnt=0
read -p "Enter some ints to be counted : " int

while [[ $(("int" % 2)) != 0 ]]
do
    cnt=$(( cnt + 1 ))
    read -p "Enter even int to end input : " int
done

echo "Number of odd numbers is $cnt"
