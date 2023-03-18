package org.miage.offre;

import static io.restassured.RestAssured.when;

import java.util.UUID;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.miage.offre.Entity.Offre;
import org.miage.offre.boundary.CandidatureResource;
import org.miage.offre.boundary.OffreResource;
import org.miage.offre.boundary.RecrutementResource;
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
"spring.jpa.defer-datasource-initialization=false"})
class ProjetApiApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    OffreResource or;
    @Autowired
    CandidatureResource cr;
    @Autowired
    RecrutementResource rr;

    @BeforeEach
    public void setupContext() {
        rr.deleteAll();
        cr.deleteAll();
        or.deleteAll();
        RestAssured.port =port;
    }

    @Test
    void ping () {
        when().get("/offres").then().statusCode(HttpStatus.SC_OK);
    }

    @Test
    void getAll() {
        Offre o1 = new Offre("1","bonjourTest", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o1);
        Offre o2 = new Offre("2","bonjour", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o2);
        Offre o3 = new Offre("3","bonjourm", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o3); 
        when().get("/offres").then().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("_embedded.offreList.size()",equalTo(3));
        }

        @Test
        void getOne() {
            Offre o1 = new Offre("1","developpement appli web", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
            or.save(o1);
            Offre o2 = new Offre("2","developpement", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
            or.save(o2);
            when().get("/offres/2").then().statusCode(HttpStatus.SC_OK)
                .and().assertThat().body("id",equalTo("2")).body("nomStage", equalTo("developpement"));
            }


            @Test
            void getOneExistePas() {
                Offre o1 = new Offre("1","developpement appli web", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
                or.save(o1);
                when().get("/offres/2").then().statusCode(HttpStatus.SC_NOT_FOUND);
                }

    }
