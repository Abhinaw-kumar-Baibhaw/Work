FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/SocialMediaApiGateway-0.0.1-SNAPSHOT.jar socialmediaapi.jar
ENTRYPOINT ["java", "-jar", "socialmediaapi.jar"]
EXPOSE 8080