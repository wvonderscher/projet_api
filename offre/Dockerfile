FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp 
ADD target/projet_api-0.0.1-SNAPSHOT.jar projet_api-0.0.1-SNAPSHOT.jar
RUN bash -c 'touch /projet_api-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar" ,"projet_api-0.0.1-SNAPSHOT.jar"]