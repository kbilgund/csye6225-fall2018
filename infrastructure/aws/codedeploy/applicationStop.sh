#!/bin/bash

process_id=`/bin/ps -ef | grep "nohup" | grep -v "grep" | awk '{print $2}'`
echo $process_id
sudo kill -9 $process_id
exit 0
