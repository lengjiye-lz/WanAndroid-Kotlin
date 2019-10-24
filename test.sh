#!/bin/bash
while [ "1" = "1" ]  #死循环
do
    echo "滑动屏幕"
    adb shell input swipe 500 1200 500 890  1000
    sleep 2
done