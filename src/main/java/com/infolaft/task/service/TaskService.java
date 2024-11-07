package com.infolaft.task.service;

import com.infolaft.task.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> findAllTasks();
    Optional<Task> findTaskById(Long id);
    Task saveTask(Task task);
    void deleteTask(Long id);
}
