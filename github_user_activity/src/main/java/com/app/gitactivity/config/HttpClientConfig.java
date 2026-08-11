package com.app.gitactivity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient httpClient() {
        // Built once and shared across the entire application
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }
}

