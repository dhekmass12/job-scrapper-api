FROM gradle:jdk21-alpine
WORKDIR /app
COPY ./job-scrapper-api-1.0-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java","-jar","job-scrapper-api-1.0-SNAPSHOT.jar"]