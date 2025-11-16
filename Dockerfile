FROM quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21.0.9 AS build

WORKDIR /project

COPY mvnw . 
COPY .mvn .mvn
COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src ./src

RUN ./mvnw clean package \
    -Dquarkus.package.type=native \
    -Dquarkus.native.enable-http-url-handler=true \
    -DskipTests

FROM quay.io/quarkus/quarkus-micro-image:2.0-2025-10-19

WORKDIR /work/

COPY --from=build /project/target/*-runner /work/application

EXPOSE 8080

ENTRYPOINT ["./application"]

