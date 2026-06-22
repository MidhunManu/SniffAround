package com.sniffaround.config;

import com.sniffaround.Service.MinioService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {
    @Bean
    @Primary
    public MinioService minioService() {
        return Mockito.mock(MinioService.class);
    }
}
