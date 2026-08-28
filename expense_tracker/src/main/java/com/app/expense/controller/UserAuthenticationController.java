package com.app.expense.controller;

import com.app.expense.response_dto.UserResponseDTO;
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
            String userName = loginRequest.get("username");
            String password = loginRequest.get("password");
            logger.info("In controller {}", userName);
            UserResponseDTO userResponseDTO = userAuthenticationService.login(userName, password);
            logger.info(userResponseDTO.toString());
            return ResponseEntity.ok(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getLocalizedMessage());
        }
    }
}
