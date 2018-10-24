#!/bin/bash
cd /home/centos/
sudo chown -R centos:centos /home/centos/cloudClass1-1.0-SNAPSHOT.jar
sudo nohup java -jar -Dspring.profiles.active=dev cloudClass1-1.0-SNAPSHOT.jar > /dev/null 2>&1  &
