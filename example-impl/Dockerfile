#this file should be ran from API root folder
FROM maven:3.6-jdk-11-slim AS compiler
WORKDIR /opt/fast-api
COPY ./pom.xml ./
COPY ./commons/ ./commons

COPY ./example-impl/ ./example-impl

RUN mvn -B -f pom.xml dependency:go-offline
RUN mvn -B  clean compile package -DskipTests

FROM openjdk:11-jdk-slim
WORKDIR /opt/fast-api
COPY --from=compiler /opt/fast-api/example-impl/target/fast-api-example-0.0.1-SNAPSHOT.jar .
RUN date >/build-date.txt
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "./fast-api-example-0.0.1-SNAPSHOT.jar"]
