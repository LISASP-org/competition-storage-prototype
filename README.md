# competition-storage-prototype
Stores competitions with their results and additional attachments.

## Setup

### Prerequisites

- maven, jdk 11
- mongodb (setup see ```setup/docker/mongodb```)
- mssql (setup see ```setup/docker/mssql```)
  - create database ```competition-storage``` with same username and password. 
  - execute query ```setup/sqlserver/InitializeDatabase.sql```
- keycloak (setup see ```setup/docker/keycloak```)

### Build

- copy ```src/main/resources/application-dev.yml.example``` to ```src/main/resources/application-dev.yml```
- edit ```src/main/resources/application-dev.yml``` (if needed for mongodb)
- build ```mvn clean package``` from the project directory

## Execute

- run ```mvn spring-boot:run``` from the project directory
