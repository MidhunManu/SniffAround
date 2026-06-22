package com.sniffaround.config;

import com.sniffaround.Service.MinioService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public MinioService minioService() {
        return Mockito.mock(MinioService.class);
    }
}
