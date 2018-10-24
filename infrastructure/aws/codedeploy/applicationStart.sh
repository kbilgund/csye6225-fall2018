#!/bin/bash
sudo nohup java -jar -Dspring.profiles.active=dev /home/centos/cloudClass1-1.0-SNAPSHOT.jar > test.txt 2>&1 </dev/null &
