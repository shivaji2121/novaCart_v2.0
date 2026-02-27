package com.novaCart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class NovaCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovaCartApplication.class, args);
	}

}
