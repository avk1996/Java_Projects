package com.app.gitactivity.service;

import com.app.gitactivity.utility.GitHubLink;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Service
@Transactional
public class GitHubActivityService {

    private static final Logger LOGGER = Logger.getLogger(GitHubActivityService.class.getName());

    private final HttpClient httpClient;

    // Spring automatically injects the HttpClient bean from HttpClientConfig
    @Autowired
    public GitHubActivityService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Autowired
    private GitHubLink gitHubLink;

    public String getJSON(String userName) {
        String url = gitHubLink.getGitEventURL(userName);
        LOGGER.info(url);
        try{
            return fetchJsonData(url);
        }catch (Exception e){
            throw new RuntimeException("API Call Failed");
        }
    }

    public String getTodayActivity(String userName) {
        try{
            String url = gitHubLink.getGitEventURL(userName);
            JSONArray jsonArray = new JSONArray(fetchJsonData(url));
            int jsonLength = jsonArray.length();
            LOGGER.info("json Size: "+jsonLength);
            int commits = 0;
            String gitInfo = "";
            for(int i=0;i<jsonLength;i++){
                JSONObject person = jsonArray.getJSONObject(i);
                String commitDate = person.getString("created_at");
                String user = person.getJSONObject("actor").getString("display_login");
                String repoName = person.getJSONObject("repo").getString("name");

                if(String.valueOf(LocalDate.now()).equals(commitDate.replaceAll("T.*", "")))
                    commits++;
                gitInfo = user + " Pushed "+ commits + " commits to " +repoName;
            }
            return gitInfo;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Builds the request dynamically and fetches JSON data from the provided URL.
     */
    public String fetchJsonData(String url) {
        try {
            // Build the dynamic request using your exact configuration
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("User-Agent", "Java-HttpClient-App")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            // Execute the request using the injected client
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body(); // Returns the raw JSON string
            } else {
                throw new RuntimeException("Failed to fetch data. HTTP Status: " + response.statusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error executing HTTP request", e);
        }
    }
}
