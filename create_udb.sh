#!/bin/bash -l

UDB_DIR=src/main/resources/analyze/udb/$2.udb
REP_DIR=src/main/resources/analyze/repository/$2
MAIN_UDB=src/main/resources/analyze/repository/db.udb

if [ -d ${REP_DIR} ]; then
    rm -rf ${REP_DIR}
fi

if ! [ -f ${MAIN_UDB} ]; then
    und create -db ${MAIN_UDB} -languages java
fi

git clone $1 ${REP_DIR}
#und create -db ${UDB_DIR} -languages java add ${REP_DIR} analyze
#und -db ${MAIN_UDB} remove ${REP_DIR}
und -db ${MAIN_UDB} add ${REP_DIR} analyze