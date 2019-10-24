#!/bin/bash
while [ "1" = "1" ]  #死循环
do
    echo "点击"
    adb shell input tap 1050 60
    sleep 3
done