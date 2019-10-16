#!/bin/bash

res=1
mode="+"

echo $$ > task5.tmp

(tail -n 0 -f "task5.tmp") |
while true
do
    read s
    case $s in
        "+")
            mode="+"
            echo "Changed mode to the +"
            ;;
        "*")
            mode="*"
            echo "Changed mode to the *"
            ;;
        "QUIT")
            echo "Stopped by QUIT message"
            rm task5.tmp
            exit 0
            ;;
        *)
            if [[ "$s" =~ [0-9]+ ]]
            then
                case $mode in
                    "+")
                        res=$(($res + $s))
                        echo $res
                        ;;
                    "*")
                        res=$(($res * $s))
                        echo $res
                        ;;
                esac
            else
                echo "Stopped by incorrect message"
                rm task5.tmp
                exit 1
            fi
            ;;
    esac

    sleep 1
done
