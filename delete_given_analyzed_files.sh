#!/usr/bin/env bash

UDB_FILE=$2
REP_DIR=src/main/resources/analyze/repository/$1

if [ -f ${UDB_FILE} ]; then
    rm ${UDB_FILE}
fi

if [ -d ${REP_DIR} ]; then
    rm -rf ${REP_DIR}
fi