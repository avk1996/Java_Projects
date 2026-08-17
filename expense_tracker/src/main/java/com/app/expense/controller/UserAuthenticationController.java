package com.app.expense.controller;

import com.app.expense.entity.User;
import com.app.expense.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/expense/auth")
public class UserAuthenticationController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest){
        try {
            String userName = loginRequest.get("user_name");
            String password = loginRequest.get("password");

            User loggedInUser = userAuthenticationService.loginCheck(userName, password);

            return ResponseEntity.ok(loggedInUser);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getLocalizedMessage());
        }
    }
}
