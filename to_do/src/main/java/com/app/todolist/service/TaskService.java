package com.app.todolist.service;

import com.app.todolist.dao.TaskDao;
import com.app.todolist.entity.Task;
import com.app.todolist.utility.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class TaskService {

    private static final Logger LOGGER = Logger.getLogger(TaskService.class.getName());

    @Autowired
    private TaskDao taskDao;

    public void createNewTask(String goal, String description) {
        try {
            Task task = new Task(goal.trim(), description.trim());
            task.setCreated(LocalDateTime.now());
            taskDao.save(task);
        }catch (Exception e){
            LOGGER.log(Level.SEVERE, "Unable to save task");
        }
    }

    public Task deleteTask(Integer id) {
        try{
            Task task = taskDao.findById(id).orElse(null);
            taskDao.deleteById(id);
            return task;
        }catch(Exception e){
            LOGGER.log(Level.WARNING, "Task is unavailable to remove");
            return null;
        }
    }

    public Task updateTask(int id, Task task) {
        try{
            Task existingTask = taskDao.getReferenceById(id);
            System.out.println(existingTask);
            existingTask.setGoal(task.getGoal());
            existingTask.setDescription(task.getDescription());
            existingTask.setCreated(task.getCreated());
            existingTask.setUpdated(LocalDateTime.now());
            existingTask.setStatus(task.getStatus());
            return existingTask;
        } catch (EntityNotFoundException e) {
            LOGGER.info("Task was unable to update: "+e.getLocalizedMessage());
            return null;
        }
    }

    public List<Task> getTasks() {
        try {
            return taskDao.findAll();
        }catch (Exception e){
            LOGGER.info("Task does not exists: "+e.getLocalizedMessage());
            return null;
        }
    }

    public Task getTask(Integer id) {
        try{
            return taskDao.findById(id).orElse(null);
        } catch (EntityNotFoundException e) {
            LOGGER.info("Task of id: "+id+", does not present at the moment: "+e.getLocalizedMessage());
            return null;
        }
    }

    public boolean updateTaskStatus(int id, TaskStatus status) {
        try{
            Task task = getTask(id);
            task.setStatus(status);
            task.setUpdated(LocalDateTime.now());
            return true;
        } catch (RuntimeException e) {
            LOGGER.info("Unable to update task of id: "+id+", of status: "+status+", Error: "+e.getLocalizedMessage());
            return false;
        }
    }

    public List<Task> getDoneTasks() {
        try {
            return taskDao.findByStatusEquals(TaskStatus.DONE);
        }catch (Exception e){
            LOGGER.info("Done Tasks not available: "+e.getLocalizedMessage());
            return null;
        }
    }

    public List<Task> getNotDoneTasks() {
        try {
            return taskDao.findByStatusEquals(TaskStatus.NOT_DONE);
        }catch (Exception e){
            LOGGER.info("Not Done Tasks not available: "+e.getLocalizedMessage());
            return null;
        }
    }

    public List<Task> getInProgressDoneTasks() {
        try {
            return taskDao.findByStatusEquals(TaskStatus.IN_PROGRESS);
        }catch (Exception e){
            LOGGER.info("In Progress Tasks not available: "+e.getLocalizedMessage());
            return null;
        }
    }
}
