#!/bin/bash

outFile="log/task1.log"
userName="bob"

ps -a -u $userName -o pid,cmd | awk '{ print $1 " : " $2 }' > $outFile
wc -l $outFile | awk '{ print $1 }'
