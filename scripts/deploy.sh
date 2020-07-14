#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2

echo "> 10초후 스크립트를 실행합니다."

sleep 10

echo "> deploy.sh에 권한을 부여합니다."

chmod +x ./secondDeploy.sh

echo "> secondDeploy.sh 를 실행합니다."

nohup ./secondDeploy.sh \\deploy > $REPOSITORY/deploy.out  2>&1 &
