#!/bin/bash

trashFolder=$HOME/.trash
logFile=$HOME/.trash.log

fileName=$1

if [[ ! -d "$trashFolder" ]]; then
    mkdir $trashFolder
fi

cnt=0
trashName="$fileName-$cnt"
while [[ -e "$trashFolder/$trashName" ]] ; do
    $(( cnt=cnt+1 ))
    trashName="$fileName-$cnt"
done

ln $fileName "$trashFolder/$trashName"
rm $fileName && echo "$(realpath $fileName) $trashName" >> "$logFile"
