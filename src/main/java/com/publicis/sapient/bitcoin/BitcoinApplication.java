package com.publicis.sapient.bitcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.publicis.sapient.bitcoin.*"})
public class BitcoinApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BitcoinApplication.class, args);
	}

}
