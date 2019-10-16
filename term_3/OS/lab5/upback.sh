#!/bin/bash

restoreDir="$HOME/restore"

launchDate=$(date "+%F")

formatDate () {
    echo $(date -d "$1" "$2")
}

date2Timestamp () {
    echo $(formatDate "$1" "+%s")
}

getNewBackupPath () {
    curTsDate=$(date2Timestamp "${launchDate}")
    newBackupDate=$(getNewBackupDate)
    if [[ ! -z $newBackupDate ]]; then
        echo "${HOME}/Backup-${newBackupDate}"
    else
        echo ""
    fi
}

getNewBackupDate () {
    newDate=$(ls ${HOME} |
        grep -P "^Backup-\d{4}-\d{2}-\d{2}$" |
        cut -d "-" -f 2,3,4 |
        sort --numeric-sort --reverse |
        head -n 1)
    echo "$newDate"
}

restore () {
    backup_dir=$1
    mkdir ${restoreDir}
    ls -A ${backup_dir} |
        grep --invert -P ".*\d{4}-\d{2}-\d{2}$" |
        awk -v backup_dir="${backup_dir}" '{ print backup_dir "/" $1 }' |
        xargs cp -rvf --target=${restoreDir}
}

main () {
    newBackupPath=$(getNewBackupPath)
    echo $newBackupPath
    restore "${newBackupPath}"
}


main
