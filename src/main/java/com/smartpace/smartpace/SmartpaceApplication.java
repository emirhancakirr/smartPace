package com.smartpace.smartpace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.smartpace.smartpace.config.Concept2ApiProperties;

@SpringBootApplication
@EnableConfigurationProperties(Concept2ApiProperties.class)
public class SmartpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartpaceApplication.class, args);
	}

}
