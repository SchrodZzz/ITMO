#!/bin/bash

grep -soH -E "^#!/bin/[[:alnum:]]+" /bin/*
