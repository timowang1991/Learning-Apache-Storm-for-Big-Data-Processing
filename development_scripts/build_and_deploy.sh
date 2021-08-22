#!/bin/sh

set -e

docker exec -it maven bash -c "mvn clean install"

sh development_scripts/deploy.sh