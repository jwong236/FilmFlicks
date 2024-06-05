# download all the necessary software to run maven (search this maven base image in DockerHub to see what is included)
FROM maven:3.8.5-openjdk-11-slim AS builder

# create and `cd` into a folder called "app" inside the virtual machine
WORKDIR /app

# copy everything in the current folder into the "app" folder. (src/ WebContent/ etc)
COPY ../ .

# compile the application inside the "app" folder to generate the war file
RUN mvn clean package

# download all the necessary software to run tomcat (this is another base image)
FROM tomcat:10-jdk11

WORKDIR /app

COPY --from=builder /app/target/fabFlix.war /usr/local/tomcat/webapps/fabFlix.war

# open the 8080 port of the container, so that outside requests can reach the tomcat server
EXPOSE 8080

# start tomcat server in the foreground
CMD ["catalina.sh", "run"]