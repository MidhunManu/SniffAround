package com.sniffaround.config;

import com.sniffaround.Service.MinioService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class TestConfig {
    public MinioService minioService() {
        return Mockito.mock(MinioService.class);
    }
}
