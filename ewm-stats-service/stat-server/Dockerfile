FROM amazoncorretto:11-alpine-jdk
ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081
ADD target/stat-server-0.0.1-SNAPSHOT.jar stats.jar
ENTRYPOINT ["java","-jar","/stats.jar"]