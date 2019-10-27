package com.system.algamoney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.system.algamoney.config.property.AlgamoneyProperty;

@SpringBootApplication
@EnableConfigurationProperties(AlgamoneyProperty.class)
public class GoldVisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldVisionApplication.class, args);
	}

}

