package com.ertelecom.lesson_14;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    private static final long serialVersionUID = 908699782135348313L;
    enum Status {
          CREATED("Создана",1,1)
        , CLOSSED("Закрыта",3,2)
        , REJECTED("Отклонена",2,3);

        private String rusTitle;        // русское название
        private int order;              // порядок сортировки
        private int statusId;           // ID в базе данных

        public String getRusTitle() {
            return rusTitle;
        }

        public int getStatusId() {
            return statusId;
        }

        Status(String rusTitle, int order, int statusId) {
            this.rusTitle = rusTitle;
            this.order = order;
            this.statusId = statusId;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private long id;
    @Column(name="task_name")
    private String taskName;
    @Column(name="owner_name")
    private String ownerName;
    @Column(name="executor_name")
    private String executorName;
    @Column(name="info")
    private String info;
    @Column(name="status_id")
    private int statusId;

    public void setStatus(Status status) {
        statusId = status.getStatusId();
    }

    public Status getStatus() {
        for (Status o: Status.values() ) {
            if (o.getStatusId() == statusId) return o;
        }
        return null;
    }

    public Task() {
        this.setStatus(Status.CREATED);
    }

    public Task(long id, String taskName, String ownerName, String executorName, String info) {
        this.id = id;
        this.taskName = taskName;
        this.ownerName = ownerName;
        this.executorName = executorName;
        this.info = info;
        this.setStatus(Status.CREATED);
    }

    @Override
    public String toString() {
        return id + "; " + taskName + "; " + ownerName + "; " + executorName + "; " + info + "; " + getStatus().rusTitle;
    }


}
