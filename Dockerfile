FROM openjdk:17
LABEL maintainer="contributetocommunity"
ADD target/contribute-to-community-0.0.1-SNAPSHOT.jar contribute-to-community.jar
ENTRYPOINT ["java", "-jar", "contribute-to-community.jar"]
