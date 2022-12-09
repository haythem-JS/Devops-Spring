FROM maven:3.6.3-jdk-8 as builder

RUN mkdir -p /home/project

COPY . /home/project

WORKDIR /home/project
# Compile and package the application to an executable JAR

RUN mvn install -Dmaven.test.skip=true


FROM adoptopenjdk/openjdk8:alpine-jre as runtime

COPY --from=builder /home/project/target/*.jar /home/project/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/home/project/app.jar"]
