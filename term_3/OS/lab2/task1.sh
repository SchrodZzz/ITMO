#!/bin/bash

outFile="errors.log"

grep --only-matching --recursive --no-messages --no-filename "ACPI.*" /var/log/ > $outFile
cat $outFile | grep "/[[:alnum:]]+"

# option flags in 6 can be replaces with -orch (god bless manual 'man')
