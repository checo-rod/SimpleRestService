package com.checorod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
public class Application {

	@Bean
	public static PropertySourcesPlaceholderConfigurer createPropertyConfigurer() {
		PropertySourcesPlaceholderConfigurer propertyConfigurer = new PropertySourcesPlaceholderConfigurer();
		propertyConfigurer.setTrimValues(true);
		return propertyConfigurer;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
