package com.system.goldvision.config;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.system.goldvision.config.property.GoldVisionProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GoogleCloudStorageConfig {

    @Autowired
    private GoldVisionProperty property;

    public Credentials credentials() throws IOException {
        return GoogleCredentials
                .fromStream(new FileInputStream(property.getStorageConfig().getStorageConfigPath()));
    }

    @Bean
    public Storage storage() throws IOException {
        return StorageOptions.newBuilder().setCredentials(credentials())
                .setProjectId(property.getStorageConfig().getProjectID()).build().getService();
    }

    @Bean
    public Bucket bucket() throws IOException {
        return storage().get(property.getStorageConfig().getBucketName());
    }

}
