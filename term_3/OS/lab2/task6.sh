#!/bin/bash

find /var/log -name "*.log" -type f | xargs wc -l | tail -1 | grep -o -E "[[:digit:]]+"
