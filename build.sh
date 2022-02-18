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
docker-compose up -d
echo "########################################################################################"
echo "API:"
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' moodlelight_API_1
echo "SECURITY:"
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' moodlelight_AUTH_1
echo "RUNNER:"
docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' moodlelight_RUNNER_1
echo "########################################################################################"
echo "USER -> API -> AUTH -> API -> USER"
containerUrl=$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' moodlelight_API_1)
curl -X POST "http://${containerUrl}:8080/api/auth/signin" -d "{\"username\":\"testStudent1\",\"password\":\"password\"}" -H "Content-Type: application/json"
echo "\n########################################################################################"
