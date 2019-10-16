#!/bin/bash

if [[ $# -ne 2 ]]; then
    echo -e "Incorrect amount of arguments ::\n\tProvide only 2 arguments"
else
    if [[ "$1" == "$2" ]]; then
        echo "Strings are equal! ;3"
    else
        echo "Strings are NOT equal! :-("
    fi
fi

# () - запуск подпроцесса (отдельного процесса bash)
# [] - синоним команды test (бастрее, тк является  встроеной командой)
# [[]] - более новый аналог test c расшириным функционалом
