FROM gradle:8.3.0 AS build

WORKDIR ./build
COPY . ./
RUN gradle assemble

FROM openjdk:17.0.2-jdk

WORKDIR /app
COPY --from=build  /home/gradle/build/build/libs/employees.jar /app/

EXPOSE 8080
CMD ["java", "-jar", "employees.jar"]