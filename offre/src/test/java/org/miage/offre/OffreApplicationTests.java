package org.miage.offre;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
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
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
"spring.datasource.url=jdbc:h2:mem:m2db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
"spring.datasource.driver-class-name=org.h2.Driver",
"spring.datasource.username=sa",
"spring.datasource.password=password",
"spring.jpa.hibernate.ddl-auto=create-drop",
"spring.jpa.defer-datasource-initialization=false"})
class OffreApplicationTests {

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

    // Test requête vers API
    @Test
    void ping () {
        when().get("/offres").then().statusCode(HttpStatus.SC_OK);
    }

    // On récupère toutes les offres
    @Test
    void getAllOffres() {
        Offre o1 = new Offre("1","bonjourTest", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o1);
        Offre o2 = new Offre("2","bonjour", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o2);
        Offre o3 = new Offre("3","bonjourm", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o3); 
        when().get("/offres").then().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("_embedded.offreList.size()",equalTo(3));
        }

    // ON souhaite récuperer une offre existante
    @Test
    void getOneOffre() {
        Offre o1 = new Offre("1","developpement appli web", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o1);
        Offre o2 = new Offre("2","developpement", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o2);
        when().get("/offres/2").then().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("id",equalTo("2")).body("nomStage", equalTo("developpement"));
        }

    //On souhaite récuperer une offre qui n'existe pas
    @Test
    void getOneOffreExistePas() {
        Offre o1 = new Offre("1","developpement appli web", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o1);
        when().get("/offres/2").then().statusCode(HttpStatus.SC_NOT_FOUND);
        }

        // On recupère toutes les offres d'un domaine particulier
    @Test
    void getAllOffreParDomaine() {
        Offre o1 = new Offre("1","developpement appli web1", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o1);
        Offre o2 = new Offre("2","developpement appli web2", "a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o2);
        Offre o3 = new Offre("3","developpement appli web3", "b","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o3);
        when().get("/offres/domaine/a").then().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("_embedded.offreList.size()",equalTo(2));
        }


        // On recupère toutes les offres d'une organisation particuliere
    @Test
    void getAllOffreParOrganisation() {
        Offre o1 = new Offre("1","developpement appli web1", "a","Amazon","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o1);
        Offre o2 = new Offre("2","developpement appli web2", "a","Sogeti","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o2);
        Offre o3 = new Offre("3","developpement appli web3", "b","PWC","a","a","a","a","a","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o3);
        when().get("/offres/organisation/Sogeti").then().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("_embedded.offreList.size()",equalTo(1));
        }

    // On recupère toutes les offres d'une organisation particuliere
    @Test
    void getAllOffreParDateDebutStage() {
        Offre o1 = new Offre("1","developpement appli web1", "a","Amazon","a","a","a","a","03-04-2023","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o1);
        Offre o2 = new Offre("2","developpement appli web2", "a","Sogeti","a","a","a","a","12-04-2023","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o2);
        Offre o3 = new Offre("3","developpement appli web3", "b","PWC","a","a","a","a","03-04-2023","a","a","a","a","a","a","a","a","a","a",true);
        or.save(o3);
        when().get("/offres/dateDebut/03-04-2023").then().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("_embedded.offreList.size()",equalTo(2));
        }
    // On récupère toutes les offres d'un lieu de stage précis
        @Test
    void getAllOffreParLieuDeStage() {
        Offre o1 = new Offre("1","developpement appli web1", "a","Amazon","a","a","a","a","a","a","a","a","a","a","a","a","Nancy","a","a",true);
        or.save(o1);
        Offre o2 = new Offre("2","developpement appli web2", "a","Sogeti","a","a","a","a","a","a","a","a","a","a","a","a","Nancy","a","a",true);
        or.save(o2);
        Offre o3 = new Offre("3","developpement appli web3", "b","PWC","a","a","a","a","a","a","a","a","a","a","a","a","Paris","a","a",true);
        or.save(o3);
        when().get("/offres/lieu/Nancy").then().statusCode(HttpStatus.SC_OK)
            .and().assertThat().body("_embedded.offreList.size()",equalTo(2));
        }

        // Ajout d'une offre avec requête POST
        @Test
    void postOffre() throws JSONException {
        JSONObject empParams = new JSONObject();
        empParams.put("nomStage", "Dev");
        empParams.put("domaine", "a");
        empParams.put("nomOrganisation", "EY");
        empParams.put("descriptionStage", "a");
        empParams.put("datePublicationOffre", "a");
        empParams.put("niveauEtudeStage", "bac+5");
        empParams.put("experienceRequiseStage", "aucune");
        empParams.put("dureeStage", "a");
        empParams.put("salaireStage", "a");
        empParams.put("organisationAdresse", "a");
        empParams.put("organisationMail", "a");
        empParams.put("organisationTel", "a");
        empParams.put("organisationURL", "a");
        empParams.put("lieuStageAdresse", "a");
        empParams.put("lieuStageTel", "a");
        empParams.put("lieuStageURL", "a");

        given().contentType(ContentType.JSON)
        .body(empParams.toString())
        .log().all()
        .when()
        .post("/offres")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

        when().get("/offres").then().statusCode(HttpStatus.SC_OK)
        .and().assertThat().body("_embedded.offreList.size()",equalTo(1));

        when().get("/offres/organisation/EY").then().statusCode(HttpStatus.SC_OK)
        .and().assertThat().body("_embedded.offreList.size()",equalTo(1));
    }

    // On récupère toutes les offres d'un lieu de stage précis
    @Test
void deleteOffre() {
    Offre o1 = new Offre("1","developpement appli web1", "a","Amazon","a","a","a","a","a","a","a","a","a","a","a","a","Nancy","a","a",true);
    or.save(o1);
    Offre o2 = new Offre("2","developpement appli web1", "a","Amazon","a","a","a","a","a","a","a","a","a","a","a","a","Nancy","a","a",true);
    or.save(o2);
    when().delete("/offres/1").then().statusCode(HttpStatus.SC_NO_CONTENT);

    when().get("/offres").then().assertThat().body("_embedded.offreList.size()",equalTo(1));
    }


    @Test
    void patchOffre(){
        Offre o1 = new Offre("1","developpement appli web1", "a","Amazon","a","a","a","a","a","a","a","a","a","a","a","a","Nancy","a","a",true);
        or.save(o1);

        when().patch("/offres/offre/1/fermer").then().statusCode(HttpStatus.SC_OK);
        when().get("/offres/1").then().assertThat().body("vacante", equalTo(false));
    }


    }
