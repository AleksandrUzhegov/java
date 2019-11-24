package com.ertelecom.lesson_12;

import java.sql.SQLException;
import java.util.List;

public interface TaskRepository {
    void prepare() throws SQLException, ClassNotFoundException;

    boolean addTask(Task obj) throws SQLException;
    Task getTaskById(long id);

    default void addTask(int id, String taskName, String ownerName, String executorName, String info) throws SQLException {
        if (getTaskById(id) != null) {
            throw new AlreadyExistsException("Задача с ID = " + id + " уже существует!");
        } else if (!addTask(new Task(id, taskName, ownerName, executorName, info))) {
            throw new ListFullException("Не удалось создать задачу с ID = " + id );
        }
    }

    void delTaskById(long id);
    void delTaskByName(String name);
    void setStatusById(long id, Task.Status status);

    void clear();
    List<Task> getList();
    List<Task> getTaskByStatus(Task.Status status );

    // печать списка
    default void print() {
        List<Task> tasks = getList();
        for (Task obj : tasks) System.out.println(obj);
    }



}
