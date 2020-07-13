#### Stage 1: Build the application
FROM openjdk:11-jdk-slim as build


#ENV JAVA_HOME="/usr/lib/jvm/default-jvm/"
#RUN apk add openjdk11

# Has to be set explictly to find binaries 
#ENV PATH=$PATH:${JAVA_HOME}/bin

# https://docs.oracle.com/javase/10/tools/jshell.htm
# https://en.wikipedia.org/wiki/JShell
#CMD ["jshell"]

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Build all the dependencies in preparation to go offline. 
# This is a separate step so the dependencies will be cached unless 
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

# Copy the project source
COPY src src

# Package the application
RUN ./mvnw package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

#### Stage 2: A minimal docker image with command to run the app 
FROM openjdk:11-jre-slim

#ARG DEPENDENCY=/app/target/dependency

# Copy project dependencies from the build stage
COPY --from=build /app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=build /app/target/dependency/META-INF /app/META-INF
COPY --from=build /app/target/dependency/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","ar.com.clazz.beersapi.BeerApiApplication"]