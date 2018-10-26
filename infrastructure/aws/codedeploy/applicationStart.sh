#!/bin/bash
cd /home/centos/
sudo chown -R centos:centos /home/centos/cloudClass1-1.0-SNAPSHOT.jar
source /app
nohup java -jar -Dspring.profiles.active=prod cloudClass1-1.0-SNAPSHOT.jar --server.port=80 > /dev/null 2>&1  &
