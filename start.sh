#!/bin/bash -l

export DOCKER_HOST=unix:///var/run/docker.sock
echo $DOCKER_HOST

chmod +x *.sh

clean_analyzed_files.sh

gradle build -x test
docker build -t deadcode-detector:latest .
docker run -it -p 29011:29011 deadcode-detector:latest
#docker run -it -p 8080:8080 deadcode-detector:latest

