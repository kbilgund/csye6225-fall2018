#!/bin/bash
sudo nohup java -jar -Dspring.profiles.active=dev /home/centos/cloudClass1-1.0-SNAPSHOT.jar /dev/null 2> /dev/null < /dev/null &
