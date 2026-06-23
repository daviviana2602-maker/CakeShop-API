package br.com.davi.spring_boot_first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CakeShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(CakeShopApplication.class, args);
		System.out.println("Spring Boot Application Started");
	}

}