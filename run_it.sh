#!/usr/bin/env bash

set -e
set -o pipefail

# Build it
./gradlew clean build -x test

# Dockerize and tag it.
tag="local"
docker build . -t "openbrewerydb:$tag"
export TAG=$tag

# Deploy to docker-compose
docker-compose up -d
