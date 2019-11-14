package com.ertelecom.lesson_8;

import java.util.List;

public interface TaskService {
    void prepare();

    boolean addTask(Task obj);
    Task getTaskById(long id);

    default void addTask(int id, String taskName, String ownerName, String executorName, String info) {
        if (getTaskById(id) != null) {
            throw new AlreadyExistsException("Задача с ID = " + id + " уже существует!");
        } else if (!addTask(new Task(id, taskName, ownerName, executorName, info))) {
            throw new ListFullException("Список задач заполнен");
        }
    }

    void delTaskById(long id);
    void delTaskByName(String name);
    void print();
    void closeTaskByID(long id);
    void rejectTaskByID(long id);


    List<Task> getTaskByStatus(Task.Status status );
    boolean existsTaskById(long id);
    List<Task> getSortedTaskByStatus();
    long getCountByStatus(Task.Status status );


    }
