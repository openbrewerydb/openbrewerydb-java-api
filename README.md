# openbrewerydb-java-api

## Dependencies

* Java 16 (See [SDKMan](https://sdkman.io/) for JDK management)
* Gradle
* Docker
* Docker Compose

## Getting Started

### Quick

The run script will build the app, dockerize it, and deploy to docker compose automatically.

```bash
./run_it.sh
```

### Manual

* Run `./gradlew clean build` to build the .jar locally.
* Dockerize it with `docker build .`.
* Substitute the tag in the `docker.compose.yml` file and run `docker-compose up -d` to deploy it locally.

## Useful Docker Commands

* `docker-compose ps` - Shows which docker containers are running.
* `docker logs -f openbrewerydb` - Shows the server logs.
* `docker-compose stop` - Stops all containers
* `docker-compose up` - Starts all containers, or restarts if there were changes.
