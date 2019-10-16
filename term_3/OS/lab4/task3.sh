#!/bin/bash

crontab -l > tmp_file
echo "*/5 * * * 6 task1.sh" >> tmp_file
crontab tmp_file
rm tmp_file
