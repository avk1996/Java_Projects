package com.app.todolist.service;

import com.app.todolist.dao.TaskDao;
import com.app.todolist.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskDao taskDao;

    public void createNewTask(String goal, String description, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            Task task = new Task(goal, description, startTime, endTime);
            System.out.println(task);
            taskDao.save(task);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Task deleteTask(Integer id) {
        try{
            Task task = taskDao.findById(id).orElse(null);
            taskDao.deleteById(id);
            return task;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
