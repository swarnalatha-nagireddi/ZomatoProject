package com.prokarma.zomato;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.prokarma.zomato.dao","com.prokarma.zomato.dto"})
public class ZomatoApiIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZomatoApiIntegrationApplication.class, args);
	}

}

