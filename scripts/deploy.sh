#!/bin/bash

echo "> deploy.sh에 권한을 부여합니다."

chmod +x ./secondDeploy.sh

echo "> secondDeploy.sh 를 실행합니다."

nohup deploy.sh