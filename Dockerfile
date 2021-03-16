FROM maven:3.6.3-openjdk-15

ADD . /debertsApp
WORKDIR /debertsApp

# Is everything ok?
RUN ls

# Run Maven build
RUN mvn clean install