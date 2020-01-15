#!/bin/bash
while [ "1" = "1" ]  #死循环
do
    echo "滑动屏幕"
    adb shell input swipe 500 1500 500 890 100
    sleep 5
done