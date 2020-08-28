#!/bin/bash
while [ "1" = "1" ]  #死循环
do
    echo "点击"
    adb shell input tap 974 1987
    sleep 1
done