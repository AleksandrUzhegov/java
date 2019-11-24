package com.ertelecom.lesson_12;

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
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    List<Task> getList() {
        return taskRepository.getList();
    }

    void addTask(Task task) throws SQLException {
        taskRepository.addTask(task);
    }
    void addTask(int id, String taskName, String ownerName, String executorName, String info) throws SQLException {
        taskRepository.addTask(id, taskName, ownerName, executorName, info);
    }

    void delTaskById(long id) {
        taskRepository.delTaskById(id);
    }


    void print() {
        taskRepository.print();
    }

}
