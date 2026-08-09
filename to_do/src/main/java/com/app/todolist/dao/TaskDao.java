package com.app.todolist.dao;

import com.app.todolist.entity.Task;
import com.app.todolist.utility.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<Task, Integer> {
    List<Task> findByStatusEquals(TaskStatus status);
}
