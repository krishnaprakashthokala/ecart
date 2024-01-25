#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package


#
# Package stage
#
FROM openjdk:11
COPY --from=build /home/app/target/spring-boot-ecommerce-0.0.1-SNAPSHOT.jar /usr/local/lib/spring-boot-ecommerce.jar
EXPOSE 8443
ENTRYPOINT ["java","-jar","/usr/local/lib/spring-boot-ecommerce.jar"]