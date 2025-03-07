FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/SocialMediaUser-0.0.1-SNAPSHOT.jar socialmediauser.jar
ENTRYPOINT ["java", "-jar", "socialmediauser.jar"]
EXPOSE 8080