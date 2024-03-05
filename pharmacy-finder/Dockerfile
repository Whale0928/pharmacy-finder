FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Soeul
ENTRYPOINT ["java","-jar","/app.jar"]
