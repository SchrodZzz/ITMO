#!/bin/bash

outFile="log/task5.log"

for pid in $(ps ax -o pid | tail -n +2); do
    statusPid=$(grep -P "^Pid:" /proc/$pid/status | grep -o -P '\d*')
    statusPpid=$(grep -P "^PPid:" /proc/$pid/status | grep -o -P '\d*')
    sleepAvg=$(grep -P "avg_atom" /proc/$pid/sched | grep -o -P '\d*')

    if [[ -z $statusPid ]] ; then
        continue
    fi

    if [[ -z $statusPpid ]] ; then
        statusPpid=0
    fi

    if [[ -z $sleepAvg ]] ; then
        sleepAvg=0
    fi

    echo "ProcessID=$statusPid : Parent_ProcessID=$statusPpid : Average_Sleeping_Time=$sleepAvg"
done | sort --key=2 -V > $outFile

#ps -A -o pid | tail +2 | awk '{file="/proc/"$1"/stat";
#getline l < file; split(l,a); close(file);
#"grep -s avg_atom /proc/"$1"/sched" | getline avg_atom; split(avg_atom,b);
#print "ProcessID="a[1]" : Parent ProcessID="a[4]" : Average Sleeping Time="b[1]; }' | sort -t '=' -nk 3 > $outFile
#           works fine too
