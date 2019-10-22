FROM openjdk:12 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --no-daemon

FROM openjdk:12
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*-all.jar /app/application.jar
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/application.jar"]