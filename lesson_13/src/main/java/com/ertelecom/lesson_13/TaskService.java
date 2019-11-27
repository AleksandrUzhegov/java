package com.ertelecom.lesson_13;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Component("taskService")
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public void addTask(Task task) {
        taskRepository.addTask(task);
    }

    public void updTask(Task task)  {
        taskRepository.updTask(task);
    }

    void delTaskById(long id) {
        taskRepository.delTaskById(id);
    }

    void print() {
        taskRepository.print();
    }

}
