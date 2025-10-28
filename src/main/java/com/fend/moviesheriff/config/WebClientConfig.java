package com.fend.moviesheriff.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${external.api.base-url-tmdb}")
    private String URL_TMDB;

    @Value("${external.api.key}")
    private String API_KEY;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(URL_TMDB)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + API_KEY)
                .build();
    }
}
