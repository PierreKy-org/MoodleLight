#!/bin/sh
mvn package -Dmaven.test.skip
echo "#####################################SECURITY###########################################"
cd security
docker build -t security . --network moodle-light-network
cd ..
echo "########################################API#############################################"
cd api
docker build -t api . --network moodle-light-network
cd ..
echo "#######################################RUNNER###########################################"
cd runner
docker build -t runner . --network moodle-light-network
cd ..
echo "########################################################################################"
docker-compose up

