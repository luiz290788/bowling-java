# Bowling Java

## How to run

### Docker (Recomended)

Build the image using the follwing command

```sh
docker build -t bowling-java:latest .
```

and then run using `run.sh`

```sh
./run.sh input.txt output.txt
```

## Running locally

Build using maven

```sh
mvn package
```

and then run using the following command:

```sh
java -jar bowling-cli/target/bowling-cli-1.0-SNAPSHOT-jar-with-dependencies.jar input.txt output.txt
```

## Running tests

To run test use maven:

```sh
mvn test
```
