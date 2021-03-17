FROM maven:3.6.3-openjdk-15

# Create app-directory and copy the project
ADD . /debertsApp
WORKDIR /debertsApp

# Is everything ok?
RUN ls

# Run Maven build
RUN mvn clean install

# Remove the build container and just use the build artifact
FROM openjdk:15-jdk

LABEL maintainer="Ievgenii Izrailtenko"

VOLUME /tmp

# Add jar to container
COPY --from=0 "/debertsApp/backend/target/backend-0.0.1-SNAPSHOT.jar" deberts.jar

# Run jar-artifact
CMD ["java","-jar","/deberts.jar"]