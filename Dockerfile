FROM openjdk:8-alpine

COPY target/uberjar/meshanka.jar /meshanka/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/meshanka/app.jar"]
