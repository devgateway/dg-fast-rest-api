#this file should be ran from API root folder
FROM maven:3.6-jdk-11-slim AS compiler
WORKDIR /opt/tcdi
COPY ./pom.xml ./
COPY ./commons/ ./commons

COPY ./example-impl/ ./example-impl

RUN mvn -B -f pom.xml dependency:go-offline
RUN mvn -B  clean compile package -DskipTests

FROM openjdk:11-jdk-slim
WORKDIR /opt/fast-api
COPY --from=compiler /opt/fast-apu/example-impl/target/*.jar .
RUN date >/build-date.txt
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "./prevalence-0.0.1-SNAPSHOT.jar"]
