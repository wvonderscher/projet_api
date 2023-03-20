package org.miage.personne;

import org.junit.jupiter.api.Test;
import org.miage.personne.Entity.Personne;
import org.miage.personne.boundary.PersonneResource;
import org.springframework.boot.test.context.SpringBootTest;
import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.given;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
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
class PersonneApplicationTests {

    @LocalServerPort
    int port;

    @Autowired
    PersonneResource pr;

    @BeforeEach
    public void setupContext() {
        pr.deleteAll();
        RestAssured.port =port;
    }


	// Test requête vers API
	@Test
	void ping () {
		when().get("/users").then().statusCode(HttpStatus.SC_OK);
	}


	// On récupère toutes les offres
	@Test
	void getAllPersonne() {
		Personne p1 = new Personne("1","Michel","0102030405");
		pr.save(p1);
		Personne p2 = new Personne("2","Denis","0102030405");
		pr.save(p2);
		Personne p3 = new Personne("3","Mélanie","0102030405");
		pr.save(p3);
		when().get("/users").then().statusCode(HttpStatus.SC_OK)
			.and().assertThat().body("_embedded.personneList.size()",equalTo(3));
		}


	// On récupère un user par son nom
	@Test
	void getPersonne() {
		Personne p1 = new Personne("1","Michel","0102030405");
		pr.save(p1);
		when().get("/users/Michel").then().statusCode(HttpStatus.SC_OK)
			.and().assertThat().body("nomUser",equalTo("Michel")).and().body("telUser",equalTo("0102030405"));
		}


        // Ajout d'une offre avec requête POST
        @Test
    void postPersonne() throws JSONException {
        JSONObject empParams = new JSONObject();
        empParams.put("nomUser", "Michel");
        empParams.put("telUser", "0102030405");


        given().contentType(ContentType.JSON)
        .body(empParams.toString())
        .log().all()
        .when()
        .post("/users")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_CREATED);

        when().get("/users").then().statusCode(HttpStatus.SC_OK)
        .and().assertThat().body("_embedded.personneList.size()",equalTo(1)).and().body("_embedded.personneList[0].nomUser",equalTo("Michel"));
    }

		// On supprime une personne
		@Test
	void deletePersonne() {
		Personne p1 = new Personne("1","Michel","0102030405");
		pr.save(p1);
		Personne p2 = new Personne("2","Denis","0102030405");
		pr.save(p2);
		when().delete("/users/1").then().statusCode(HttpStatus.SC_NO_CONTENT);

		when().get("/users").then().assertThat().body("_embedded.personneList.size()",equalTo(1));
		}

		@Test
		void putPersonne() throws JSONException{
			Personne p1 = new Personne("1","Michel","0102030405");
			pr.save(p1);
			JSONObject empParams = new JSONObject();
			empParams.put("nomUser", "Micheline");
			empParams.put("telUser", "0504030201");

			given().contentType(ContentType.JSON)
			.body(empParams.toString())
			.log().all()
			.when()
			.put("/users/1")
			.then()
			.assertThat()
			.statusCode(HttpStatus.SC_OK);
	
			when().get("/users/Micheline").then().assertThat().body("telUser", equalTo("0504030201"));
		}
}

