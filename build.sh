#!/bin/sh
mvn package -Dmaven.test.skip
echo "#####################################SECURITY###########################################"
cd security
docker build -t security .
cd ..
echo "########################################API#############################################"
cd api
docker build -t api .
cd ..
echo "#######################################RUNNER###########################################"
cd runner
docker build -t runner .
cd ..
echo "################################################################################"
docker-compose up --force-recreate --build

