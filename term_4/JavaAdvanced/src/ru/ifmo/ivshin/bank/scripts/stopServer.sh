#!/usr/bin/env bash
kill -9 $(pidof rmiregistry)
kill $(ps aux | grep 'java' | awk '{print $2}')