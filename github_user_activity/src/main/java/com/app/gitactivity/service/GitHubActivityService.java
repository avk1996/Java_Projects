package com.app.gitactivity.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Logger;

@Service
@Transactional
public class GitHubActivityService {

    private static final Logger LOGGER = Logger.getLogger(GitHubActivityService.class.getName());

    public String getJSON(String userName) {
        HttpClient client = HttpClient
                .newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        String preUrl = "https://api.github.com/users/";
        String postUrl = "/events";
        String url = preUrl+userName+postUrl;

        LOGGER.info(url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("User-Agent","Java-HttpClient-App")
                .header("Accept","application/json")
                .GET()
                .build();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            LOGGER.info("Service: "+response.toString());
            return response.body();
        }catch (Exception e){
            throw new RuntimeException("API Call Failed with status code");
        }
    }
}
