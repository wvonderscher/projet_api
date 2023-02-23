package org.miage.offre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
public class ProjetApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetApiApplication.class, args);
	}

	@Bean
	public OpenAPI offreAPI(){
		return new OpenAPI().info(new Info().title("Service Offre de stage").version("1.0").description("OFFRE OFFRE OFFRE"));
	}
}
