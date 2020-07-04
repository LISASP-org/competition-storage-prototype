# competition-storage-prototype
Stores competitions with their results and additional assets.

## Setup

### Prerequisites

- maven, jdk 11
- mongodb (setup see ```setup/docker-mongodb```)

### Build

- copy ```src/main/resources/application-dev.example.yml``` to ```src/main/resources/application-dev.yml```
- edit ```src/main/resources/application-dev.yml``` (if needed for mongodb)
- build ```mvn clean package``` from the project directory

## Execute

- run ```mvn spring-boot:run``` from the project directory
