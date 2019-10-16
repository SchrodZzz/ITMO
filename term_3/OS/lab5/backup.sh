#!/bin/bash

reportFile="$HOME/backup-report"
srcDir="$HOME/source"

launchDate=$(date "+%F")

formatDate () {
    echo $(date -d "$1" "$2")
}

date2Timestamp () {
    echo $(formatDate "$1" "+%s")
}

getNewBackupPath () {
    curDate=$(date2Timestamp "${launchDate}")
    newDate=$(getNewDate)
    if [[ -z $newDate ]]; then
        echo ""
        return 0
    fi

    newTsDate=$(date2Timestamp "$newDate")
    daysDiff=$(( (newTsDate - curDate) / (60 * 60 * 24) ))

    if (( daysDiff >= 0 && daysDiff < 7 )); then
        echo "${HOME}/Backup-${newDate}"
    else
        echo ""
    fi

}

getNewDate () {
    newDate=$(ls ${HOME} |
        grep -P "^Backup-\d{4}-\d{2}-\d{2}$" |
        cut -d "-" -f 2,3,4 |
        sort --numeric-sort --reverse |
        head -n 1)
    echo "$newDate"
}

makeNewBackup () {
    backupDirName="Backup-${launchDate}"
    backupDirPath="${HOME}/${backupDirName}"
    (mkdir ${backupDirPath}  && echo "${launchDate} : Backup directory ${backupDirPath} has been created" || {
        echo "Couldn't create directory ${backupDirPath}"
        return 1
    }) >> ${reportFile}

    cp -rv "${srcDir}/"* ${backupDirPath} >> ${reportFile}
}

backup2ExistingDir () {
    backupDir=$1

    echo "${launchDate} : Changing existing backup directory ${backupDir}" >> ${reportFile}

    comm -23 <(ls -A ${srcDir}) <(ls -A ${backupDir}) |
        awk -v source_dir="${srcDir}" '{ print source_dir "/" $1 }' |
        xargs cp -rv --target=${backupDir} >> ${reportFile} || echo "No new files!"

    comm -12 <(ls -A ${srcDir}) <(ls -A ${backupDir}) |
        awk -v source_dir="${srcDir}" -v backup_dir="${backupDir}" '{
            source_cmd="wc -c " source_dir "/" $1 " | cut -d \" \" -f 1"
            backup_cmd="wc -c " backup_dir "/" $1 " | cut -d \" \" -f 1"
            source_cmd | getline source_size
            backup_cmd | getline backup_size
            if (source_size != backup_size)
                print source_dir "/" $1
            close(source_cmd)
            close(backup_cmd)
        }' |
        xargs cp -rv -b --suffix=".${launchDate}" --target=${backupDir} >> ${reportFile} ||
            echo "No files with different size!"


}

main () {
    newBackupPath=$(getNewBackupPath)
    if [[ -z "${newBackupPath}" ]] ; then
        makeNewBackup
    else
        backup2ExistingDir "${newBackupPath}"
    fi

}


main
