package com.app.expense.controller;

import com.app.expense.entity.User;
import com.app.expense.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add_user")
    public ResponseEntity<User> addUser(@RequestBody User user){
        try {
            User newUser = userService.addUser(user);
            return ResponseEntity.accepted().body(newUser);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get_users")
    public ResponseEntity<List<User>> getUsers(){
        try{
            return ResponseEntity.ok(userService.getUsers());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

}
