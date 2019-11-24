package com.ertelecom.lesson_10;

import java.io.Serializable;

public class Task implements Serializable {
    public enum Status implements Serializable {
        CREATED("Создана",1), CLOSSED("Закрыта",3), REJECTED("Отклонена",2);
        private String rusTitle;
        private int order;

        public String getRusTitle() {
            return rusTitle;
        }

        Status(String rusTitle, int order) {
            this.rusTitle = rusTitle;
            this.order = order;
        }
        }

    private long id;
    private String taskName;
    private String ownerName;
    private String executorName;
    private String info;
    private Status status;

    public long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getExecutorName() {
        return executorName;
    }

    public String getInfo() {
        return info;
    }

    public Task(long id, String taskName, String ownerName, String executorName, String info) {
        this.id = id;
        this.taskName = taskName;
        this.ownerName = ownerName;
        this.executorName = executorName;
        this.info = info;
        this.status = Status.CREATED;
    }

    @Override
    public String toString() {
        return id + "; " + taskName + "; " + ownerName + "; " + executorName + "; " + info + "; " + status.rusTitle;
    }

    // сравнение задач
    public int compareTo(Task o) {
        return this.getStatus().order - o.getStatus().order;
    }

    // закрытие задачи
    public void close() {
        status = Status.CLOSSED;
    }

    // отклонить задачу
    public void reject() {
        status = Status.REJECTED;
    }

}
