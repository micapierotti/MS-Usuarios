FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-usuario.jar
ENTRYPOINT ["java","-jar","/ms-usuario.jar"]