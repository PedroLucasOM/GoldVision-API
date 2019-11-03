package com.system.goldvision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.system.goldvision.config.property.GoldVisionProperty;

@SpringBootApplication
@EnableConfigurationProperties(GoldVisionProperty.class)
public class GoldVisionApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldVisionApplication.class, args);
	}

}

