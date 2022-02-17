#!/bin/sh
mvn package -Dmaven.test.skip
echo "################################################################################"
cd security
docker build -t security .
cd ..
echo "################################################################################"
cd api
docker build -t api .
cd ..
echo "################################################################################"
cd runner
docker build -t runner .
cd ..
echo "########################################################################################"
docker-compose up --force-recreate --build

