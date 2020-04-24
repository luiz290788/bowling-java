FROM adoptopenjdk/maven-openjdk11

RUN mkdir /opt/app
WORKDIR /opt/app

COPY . .

RUN mvn package

FROM adoptopenjdk/openjdk11

RUN mkdir /opt/app
WORKDIR /opt/app

COPY --from=0 /opt/app/bowling-cli/target/bowling-cli-1.0-SNAPSHOT-jar-with-dependencies.jar /opt/app

CMD ["java", "-jar", "bowling-cli-1.0-SNAPSHOT-jar-with-dependencies.jar"]
