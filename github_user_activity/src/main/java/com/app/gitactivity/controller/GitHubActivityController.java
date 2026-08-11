package com.app.gitactivity.controller;

import com.app.gitactivity.service.GitHubActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/git")
public class GitHubActivityController {
    private static final Logger LOGGER = Logger.getLogger(GitHubActivityController.class.getName());

    @Autowired
    private GitHubActivityService gitHubActivityService;

    @GetMapping("/activity/{userName}")
    public ResponseEntity<String> getGitHubActivity(@PathVariable String userName){
        try{
            String json = gitHubActivityService.getJSON(userName);
            return ResponseEntity.ok(json);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/activity/today/{user_name}")
    public ResponseEntity<String> getTodayActivity(@PathVariable(name = "user_name") String userName){
        try{
            return ResponseEntity.ok(gitHubActivityService.getTodayActivity(userName).trim());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
