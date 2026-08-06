package com.app.todolist.controller;

import com.app.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PutMapping("/create_task")
    public ResponseEntity<String> createNewTask(@RequestParam String goal,
                                                @RequestParam(required = false) String description,
                                                @RequestParam(required = false) LocalDateTime startTime,
                                                @RequestParam(required = false) LocalDateTime endTime){
            try {
                taskService.createNewTask(goal, description, startTime, endTime);
                return ResponseEntity.accepted().body("Task created successfully!");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Unable to create Task");
            }
    }
}
