package com.alten.shop;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@OpenAPIDefinition(
		info = @Info(title = "Alten Shop API", version = "v1"),
		security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
		name = "bearerAuth",
		type = SecuritySchemeType.HTTP,
		scheme = "bearer",
		bearerFormat = "JWT"
)
@SpringBootApplication(scanBasePackages = {"com.alten.shop"})
@ConfigurationPropertiesScan({"com.alten.shop.properties"})
public class AltenShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(AltenShopApplication.class, args);
	}

}
