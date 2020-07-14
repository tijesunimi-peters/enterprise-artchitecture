# Enterprise Architecture with JPA + EJB + JSF + JBOSS WILDFLY + Docker

## Setup
- Install dependencies with maven

- Download the mysql-conncector-java driver and put in the docker_dir/modules/mysql/main folder
You can edit the `docker_dir/configuration/standalone.xml` for further configurations of jboss/wildfly server

- Generate war file (save as `web.war`)
- Move war file to `docker_dir/web` folder 

Edit `Dockerfile` for more configurations

## Admin credentials
Goto `localhost:9992` for the server console management for wildfly

```$xslt
password: admin
username: admin
``` 

## How to run
```
docker-compose up
```
Goto `localhost:8085/web`

