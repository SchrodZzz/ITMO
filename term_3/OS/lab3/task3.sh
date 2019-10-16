#!/bin/bash

outFile="log/task3.log"

ps ax -o pid,cmd | awk '{ if($2 ~ /^\/sbin\/.*/) print $1 }' > $outFile
