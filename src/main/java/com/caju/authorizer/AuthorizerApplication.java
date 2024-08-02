package com.caju.authorizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

/**
 * Application
 * 
 * @author Felipe Figueiredo
 */
@OpenAPIDefinition(info = @Info(title = "API - Authorizer responsável por processar as transações do portador"))
@ComponentScan(basePackages = "com.caju.authorizer")
@SpringBootApplication
public class AuthorizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthorizerApplication.class, args);
	}

}
