package com.app.expense.controller;

import com.app.expense.entity.User;
import com.app.expense.response_dto.UserResponseDTO;
import com.app.expense.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody User user){
        try {
            logger.info(user.toString());
            System.out.println("controller: "+user.toString());
            UserResponseDTO newUser = userService.addUser(user);
            return ResponseEntity.accepted().body(newUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
