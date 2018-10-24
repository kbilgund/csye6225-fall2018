#!/bin/bash
nohup java -jar -Dspring.profiles.active=prod /home/centos/cloudClass1-1.0-SNAPSHOT.jar > /home/centos/log &
