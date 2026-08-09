package com.app.todolist.service;

import com.app.todolist.dao.TaskDao;
import com.app.todolist.entity.Task;
import com.app.todolist.utility.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

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

    public Task updateTask(int id, Task task) {
        try{
            Task existingTask = taskDao.getReferenceById(id);
            System.out.println(existingTask);
            existingTask.setGoal(task.getGoal());
            existingTask.setDescription(task.getDescription());
            existingTask.setStartDateTime(task.getStartDateTime());
            existingTask.setEndDateTime(task.getEndDateTime());
            existingTask.setStatus(task.getStatus());
            return existingTask;
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Task> getTasks() {
        try {
            return taskDao.findAll();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Task getTask(Integer id) {
        try{
            return taskDao.findById(id).orElse(null);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateTaskStatus(int id, TaskStatus status) {
        try{
            Task task = getTask(id);
            task.setStatus(status);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public List<Task> getDoneTasks() {
        try {
            return taskDao.findByStatusEquals(TaskStatus.DONE);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Task> getNotDoneTasks() {
        try {
            return taskDao.findByStatusEquals(TaskStatus.NOT_DONE);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Task> getInProgressDoneTasks() {
        try {
            return taskDao.findByStatusEquals(TaskStatus.IN_PROGRESS);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
