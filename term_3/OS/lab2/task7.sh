#!/bin/bash

man tr | tr -c "[:alnum:]" "\n" | grep -E ".{4,}" | sort | uniq -ic | sort -nr | head -3
