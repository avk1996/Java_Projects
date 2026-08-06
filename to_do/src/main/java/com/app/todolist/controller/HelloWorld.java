package com.app.todolist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorld {
    @GetMapping("/hello")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok().body("hello world");
    }
}
