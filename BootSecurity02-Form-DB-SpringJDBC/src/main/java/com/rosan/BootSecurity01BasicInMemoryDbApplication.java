package com.rosan;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootSecurity01BasicInMemoryDbApplication {
	


	public static void main(String[] args) {
		SpringApplication.run(BootSecurity01BasicInMemoryDbApplication.class, args);
	}

}
