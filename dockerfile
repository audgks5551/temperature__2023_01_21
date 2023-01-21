FROM eclipse-temurin:17
RUN apt-get update -y
RUN apt-get upgrade -y
RUN apt-get install -y python3 python3-pip python3-dev build-essential
RUN python3 -m pip install minimalmodbus

RUN mkdir /docker_workspace
WORKDIR /docker_workspace

COPY ./build/libs/*.jar /docker_workspace/App.jar
COPY ./python/*.py /docker_workspace/temperature.py
CMD ["java", "-jar", "/docker_workspace/App.jar"]

