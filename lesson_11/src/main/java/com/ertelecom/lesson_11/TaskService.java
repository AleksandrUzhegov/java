package com.ertelecom.lesson_11;

import org.hibernate.Session;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public interface TaskService {
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

    // сохранение списка в файл
    default void saveToFile(String filename) {
        List<Task> tasks = getList();
        try (ObjectOutputStream objOut = new ObjectOutputStream( new FileOutputStream( filename ))) {
            objOut.writeObject(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // чтение списка из файла
    default void loadFromFile(String filename) {
        List<Task> tasks;
        try (ObjectInputStream objIn = new ObjectInputStream( new FileInputStream( filename ))) {
            tasks = (List<Task>)objIn.readObject();
            clear();
            for (Task obj: tasks) {
                addTask(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    default void closeTaskById(long id) {
        setStatusById(id, Task.Status.CLOSSED);
    }

    default void rejectTaskById(long id) {
        setStatusById(id, Task.Status.REJECTED);
    }

    default boolean existsTaskById(long id) {
        return getTaskById(id) != null;
    }

    default List<Task> getSortedTaskByStatus() {
        return getTaskByStatus(null).stream().sorted((o1,o2) -> o1.compareTo(o2) ).collect(Collectors.toList());
    }

    default long getCountByStatus(Task.Status status) {
        return getTaskByStatus(status).stream().count();
    }



}
