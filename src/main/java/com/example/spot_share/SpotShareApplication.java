package com.example.spot_share;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpotShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotShareApplication.class, args);
	}

}
