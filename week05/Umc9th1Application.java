package com.example.umc9th1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Umc9th1Application {

	public static void main(String[] args) {
		SpringApplication.run(Umc9th1Application.class, args);
	}

}
