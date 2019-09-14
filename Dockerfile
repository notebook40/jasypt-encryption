FROM openjdk:8u171-jre-alpine
VOLUME /tmp
ADD target/jasypt-encryption.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
