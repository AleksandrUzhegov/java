package com.ertelecom.lesson_14;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }
    public List<Task> getTasksByFilter(Integer statusId) {
        return taskRepository.getTasksByFilter(statusId);
    }

    public void addTask(Task task) {
        taskRepository.addTask(task);
    }

    public void updTask(Task task)  {
        taskRepository.updTask(task);
    }

    public void delTaskById(long id) {
        taskRepository.delTaskById(id);
    }

    public Task getTaskById(long id) {
        return taskRepository.getTaskById(id);
    }

}
