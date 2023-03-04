package org.miage.personne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class ProjetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetApiApplication.class, args);
	}

	@Bean
	public OpenAPI personneAPI(){
		return new OpenAPI().info(new Info().title("Service personne ").version("1.0").description("PERSONNE PERSONNE PERSONNE"));
	}

	@Bean
	RestTemplate template() {
		return new RestTemplate();
	}
}
