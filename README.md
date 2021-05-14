### Prerequisites
* Java 16 (See sdkman for jdk management: https://sdkman.io/)
* Gradle
* Docker
* Docker Compose

### Building and Running
Run `./gradlew clean build` to build the .jar locally. After building the jar, dockerize it with `docker build .`.  
Substitute the tag in the `docker.compose.yml` file and run `docker-compose up -d` to deploy it locally.
Alternatively, run the `run_it.sh` script which will build the app, dockerize it, and deploy to docker compose 
automatically.

### Useful Docker Commands
`docker-compose ps` - Shows which docker containers are running.
`docker logs -f openbrewerydb` - Shows the server logs.
`docker-compose stop` - Stops all containers
`docker-compose up` - Starts all containers, or restarts if there were changes.
