#!/bin/bash -l

UDB_DIR=src/main/resources/analyze/udb/$2.udb
REP_DIR=src/main/resources/analyze/repository/$2

if [ -d ${REP_DIR} ]; then
    rm -rf ${REP_DIR}
fi

git clone $1 ${REP_DIR}
und create -db ${UDB_DIR} -languages java add ${REP_DIR}

echo ${UDB_DIR}