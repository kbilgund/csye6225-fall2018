#!/bin/bash
sudo nohup java -jar -Dspring.profiles.active=dev /home/centos/cloudClass1-1.0-SNAPSHOT.jar &
echo Done
