package org.miage.offre;

import static io.restassured.RestAssured.when;

import java.util.UUID;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.miage.offre.Entity.Offre;
import org.miage.offre.boundary.OffreResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.hamcrest.CoreMatchers.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;


import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
"spring.datasource.url=jdbc:h2:mem:m2db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
"spring.datasource.driver-class-name=org.h2.Driver",
"spring.datasource.username=sa",
"spring.datasource.password=password",
"spring.jpa.hibernate.ddl-auto=create-drop",
"spring.jpa.defer-datasource-initialization=true"})
class ProjetApiApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    OffreResource or;

    @BeforeEach
    public void setupContext() {
        or.deleteAll();
        RestAssured.port =port;
    }

    @Test
    void ping () {
        when().get("/offres").then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getAll() {
        Offre o1 = new Offre(UUID.randomUUID().toString(),"bonjour", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o1);
        when().get("/offres").then().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("size()",equalTo(1));
    }

}
