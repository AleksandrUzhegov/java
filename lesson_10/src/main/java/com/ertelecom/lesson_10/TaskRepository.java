package com.ertelecom.lesson_10;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskRepository implements TaskService {
    public static final int NONE = -1;
    private List<Task> tasks;

    public TaskRepository() {
        prepare();
    }

    @Override
    public void prepare() {
        tasks = new ArrayList();
    }

    private int findById(long id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id)  {
                return i;
            }
        }
        return NONE;
    }

    private int findByName(String name) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getTaskName().equals(name))  {
                return i;
            }
        }
        return NONE;
    }

    @Override
    public Task getTaskById(long id) {
        int index = findById(id);
        if (index >= 0 ) return tasks.get(index);
        return null;
    }

    @Override
    public boolean addTask(Task obj) {
        return tasks.add(obj);
    }

    private boolean delTask(int index) {
        return (tasks.remove(index) != null);
    }

    @Override
    public void delTaskById(long id) {
        int index = findById(id);
        if (index >= 0 ) delTask(index);
    }

    @Override
    public void delTaskByName(String name) {
        int index = findByName(name);
        if (index >= 0 ) delTask(index);
    }

    @Override
    public void print() {
        for (Task obj : tasks) System.out.println(obj);
    }

    @Override
    public void closeTaskByID(long id) {
        Task task = getTaskById(id);
        if (task != null) task.close();
    }

    @Override
    public void rejectTaskByID(long id) {
        Task task = getTaskById(id);
        if (task != null) task.reject();
    }


    // список задач по статусу
    @Override
    public List<Task> getTaskByStatus(Task.Status status ) {
        return tasks.stream().filter(t -> t.getStatus() == status).collect(Collectors.toList());
    }

    // Проверка наличия задачи с указанным ID;
    @Override
    public boolean existsTaskById(long id) {
        return tasks.stream().anyMatch(t -> t.getId() == id);
    }

    // отсортированный список задач
    @Override
    public List<Task> getSortedTaskByStatus() {
        return tasks.stream().sorted((o1,o2) -> o1.compareTo(o2) ).collect(Collectors.toList());
    }

    // Подсчет количества задач по определенному статусу;
    @Override
    public long getCountByStatus(Task.Status status ) {
        return tasks.stream().filter(t -> t.getStatus() == status).count();
    }

    @Override
    public void clear() {
        tasks.clear();
    }

    @Override
    public List<Task> getList() {
        return tasks;
    }

}
