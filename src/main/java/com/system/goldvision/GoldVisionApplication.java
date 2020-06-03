package com.system.goldvision;

import com.system.goldvision.config.property.GoldVisionProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GoldVisionProperty.class)
public class GoldVisionApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoldVisionApplication.class, args);
    }

}

