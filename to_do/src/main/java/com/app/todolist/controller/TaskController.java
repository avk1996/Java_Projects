package com.app.todolist.controller;

import com.app.todolist.entity.Task;
import com.app.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create_task")
    public ResponseEntity<String> createNewTask(@RequestParam String goal,
                                                @RequestParam(required = false) String description,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime){
            try {
                System.out.println("start time: "+startTime+", end time: "+endTime);
                taskService.createNewTask(goal, description, startTime, endTime, false);
                return ResponseEntity.accepted().body("Task created successfully!");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Unable to create Task");
            }
    }

    @PutMapping("/update_task/{id}")
    public ResponseEntity<String> updateTask(@PathVariable  int id, @RequestBody Task task){
        try {
            System.out.println(task.getStartDateTime()+", "+task.getEndDateTime());
            taskService.updateTask(id, task);
            return ResponseEntity.accepted().body("Updated Task!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Unable to update Task");
        }
    }

    @DeleteMapping("/delete_task")
    public ResponseEntity<Task> deleteTask(@RequestParam Integer id){
        try {
            return ResponseEntity.ok().body(taskService.deleteTask(id));
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("tasks")
    public ResponseEntity<List<Task>> getTasks(){
        try {
            List<Task> tasks = taskService.getTasks();
            return ResponseEntity.accepted().body(tasks);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("task")
    public ResponseEntity<Task> getTask(@RequestParam Integer id){
        try {
            Task task = taskService.getTask(id);
            return ResponseEntity.accepted().body(task);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }
}
