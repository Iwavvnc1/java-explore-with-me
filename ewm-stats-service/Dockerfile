FROM amazoncorretto:11
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081
ADD target/ewm-stats-service-0.0.1-SNAPSHOT.jar stats.jar
ENTRYPOINT ["java","-jar","/stats.jar"]