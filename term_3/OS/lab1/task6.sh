#!/bin/bash

if [[ "$PWD" == "$HOME" ]]; then
    echo "$HOME"
    exit
    # exit has 0 as default value
else
    echo "($PWD) is not home dir"
    exit 1
fi
