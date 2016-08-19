#!/bin/bash
cd /home/ubuntu/omspipelineweb
./gradlew clean
./gradlew build -x test
java -jar build/libs/gs-spring-boot-0.1.0.jar
