FROM amazoncorretto:11-alpine-jdk
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8089
ADD target/ewm-service-0.0.1-SNAPSHOT.jar service.jar
ENTRYPOINT ["java","-jar","/service.jar"]