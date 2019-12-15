package com.ertelecom.lesson_13;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.List;

public interface TaskRepository {
    void prepare() throws SQLException, ClassNotFoundException;

    void addTask(Task task);
    void updTask(Task task);

    Task getTaskById(long id);
    void delTaskById(long id);
    void setStatusById(long id, Task.Status status);
    List<Task> getAllTasks();

    // печать списка
    default void print() {
        List<Task> tasks = getAllTasks();
        for (Task obj : tasks) System.out.println(obj);
    }

}
