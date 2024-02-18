# syntax=docker/dockerfile:experimental
FROM maven:3.9.6-eclipse-temurin-21-jammy AS builder
MAINTAINER EhsanOdyssey (ehsan.shahmirzaloo@gmail.com)
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn dependency:go-offline
RUN mvn dependency:resolve-plugins
RUN --mount=type=cache,id=m2-cache,sharing=shared,target=/root/.m2 mvn clean package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:21-jre-jammy
VOLUME /tmp
ARG DEPENDENCY=/app/target/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","neo.ehsanodyssey.library.OnlineLibraryApplication"]