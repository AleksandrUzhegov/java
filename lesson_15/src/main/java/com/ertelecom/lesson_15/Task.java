package com.ertelecom.lesson_15;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    public static final int CREATED = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private long id;
    @Column(name="task_name")
    private String taskName;
    @Column(name="info")
    private String info;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="status_id")
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="owner_id")
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="executor_id")
    private User executor;

    public Task() {
       setStatusId(CREATED);
    }

    public long getStatusId() {
        return status.getStatusId();
    }

    public void setStatusId(long statusId) {
        if (status == null) status = new Status();
        status.setStatusId(statusId);
    }

    public String getOwnerName() {
        if (owner != null) return owner.getUserName();
        return "";
    }

    public String getExecutorName() {
        if (executor != null) return executor.getUserName();
        return "";
    }

    @Override
    public String toString() {
        return id + "; " + taskName + "; " + getOwnerName() + "; " + getExecutorName() + "; " + info + "; " + status.getStatusName();
    }


}
