FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp 
ADD target/offre-0.0.1-SNAPSHOT.jar offre-0.0.1-SNAPSHOT.jar
RUN bash -c 'touch /offre-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar" ,"offre-0.0.1-SNAPSHOT.jar"]