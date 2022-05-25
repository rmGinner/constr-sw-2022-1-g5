package br.rmginner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LessonsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LessonsServiceApplication.class, args);
	}

}
