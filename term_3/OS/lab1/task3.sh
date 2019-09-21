#!/bin/bash

str=""
read -p "Enter some strings to be 'concated' : " curStr

while [[ "$curStr" != "q" ]]
do
    str="$str$curStr"
    read -p "Enter 'q' to end input : " curStr
done

echo "Entered arguments:$str"
