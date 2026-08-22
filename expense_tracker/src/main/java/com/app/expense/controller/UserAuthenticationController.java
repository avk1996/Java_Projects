package com.app.expense.controller;

import com.app.expense.entity.User;
import com.app.expense.service.UserAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/expense/auth")
public class UserAuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest){
        try {
            String userName = loginRequest.get("user_name");
            String password = loginRequest.get("password");
            logger.info(">> {}", userName);
            User loggedInUser = userAuthenticationService.login(userName, password);

            return ResponseEntity.ok(loggedInUser);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getLocalizedMessage());
        }
    }
}
