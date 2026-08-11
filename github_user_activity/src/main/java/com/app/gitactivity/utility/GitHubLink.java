package com.app.gitactivity.utility;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class GitHubLink {

    public String getGitEventURL(String userName){
        String preUrl = "https://api.github.com/users/";
        String postUrl = "/events";
        return preUrl+userName+postUrl;
    }
}

