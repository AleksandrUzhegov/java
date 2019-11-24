package com.ertelecom.lesson_10;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

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
    void print();
    void closeTaskByID(long id);
    void rejectTaskByID(long id);
    void clear();
    List<Task> getList();

    List<Task> getTaskByStatus(Task.Status status );
    boolean existsTaskById(long id);
    List<Task> getSortedTaskByStatus();
    long getCountByStatus(Task.Status status );

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
            clear();
            tasks = (List<Task>)objIn.readObject();
            for (Task obj: tasks) {
                addTask(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
