FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp 
ADD target/personne-0.0.1-SNAPSHOT.jar personne-0.0.1-SNAPSHOT.jar
RUN bash -c 'touch /personne-0.0.1-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar" ,"personne-0.0.1-SNAPSHOT.jar"]