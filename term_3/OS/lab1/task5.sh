#!/bin/bash

read -p "Choose program (1 - vim; 2 - nano; 3 - links) : " cmd

case $cmd in
    1|vi)
        echo "Vim has been chosen"
        vi
        ;;
    2|nano)
        echo "Nano has been chosen"
        nano
        ;;
    3|links)
        echo "Links has been chosen"
        links
        ;;
    *)
        echo "Incorrect commmad, program closing ..."
esac
