package com.example.homework_Kristina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HomeworkKristinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeworkKristinaApplication.class, args);
	}

}
