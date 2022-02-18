#!/bin/sh
mvn package -Dmaven.test.skip
echo "################################################################################"
cd api
docker build -t api .
cd ..
echo "################################################################################"
docker-compose up -d
echo "########################################################################################"
echo "API:"
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' api
echo "SECURITY:"
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' auh
echo "RUNNER:"
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' coderunner
echo "########################################################################################"
echo "USER -> API -> AUTH -> API -> USER"
containerUrl=$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' api)
curl -X POST "http://${containerUrl}:8080/api/auth/signin" -d "{\"username\":\"testStudent1\",\"password\":\"password\"}" -H "Content-Type: application/json"
echo "\n########################################################################################"
