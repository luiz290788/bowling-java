#!/bin/bash

docker run --rm -a stdin -a stdout -i bowling-java:latest < $1 > $2