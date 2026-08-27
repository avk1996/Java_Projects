package com.app.expense.controller;

import com.app.expense.entity.User;
import com.app.expense.response_dto.UserResponseDTO;
import com.app.expense.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            return ResponseEntity.ok(userService.createUser(user));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/get_users")
    public ResponseEntity<List<UserResponseDTO>> getUsers(){
        try{
            return ResponseEntity.ok(userService.getUsers());
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/delete_user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ("id") int userId){
        try {
            return ResponseEntity.ok(userService.deleteUser(userId));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
