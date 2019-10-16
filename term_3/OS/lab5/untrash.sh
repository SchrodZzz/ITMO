#!/bin/bash

trashFolder=$HOME/.trash
logFile=$HOME/.trash.log
logFile2='logFile'0

restoreFile () {
    path=$1
    fileName=$2
    ln "$trashFolder/$fileName" $path
}


fileName=$1
line=""
grep $fileName $logFile |
while read line; do
	file2Restore=$(echo $line | cut -d " " -f 1)
	fileInTrash=$(echo $line | cut -d " " -f 2)
    echo $fileInTrash

    echo "Restore $file2Restore ? (y/n)"
    read curAns < /dev/tty
    if [[ "$curAns" == "y" ]]; then
        parentDir=$(dirname "$file2Restore") &&
            if [[ -d "$parentDir" ]]; then
                $(restoreFile "$file2Restore" "$fileInTrash")
            else
                $(restoreFile "$HOME/$fileName" "$fileInTrash") &&
                    echo "Directory $parentDir not exists anymore, restoring at $HOME"
            fi &&
            rm $trashFolder/$fileInTrash && {
                grep -v "$file2Restore $fileInTrash" $logFile > $logFile2
                echo "Restored $file2Restore"
            }
    fi
done
mv $logFile2 $logFile
