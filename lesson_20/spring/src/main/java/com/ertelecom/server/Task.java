package com.ertelecom.server;

import com.ertelecom.gwt.common.TaskDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    public static final long CREATED = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Long id;
    @Column(name="task_name")
    private String taskName;
    @Column(name="info")
    private String info;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="status_id")
    @JsonIgnore
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="owner_id")
    @JsonIgnore
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="executor_id")
    @JsonIgnore
    private User executor;

    private void init() {
        status = new Status();
        owner = new User();
        executor = new User();
        setStatusId(CREATED);
    }

    public Task() {
        init();
    }

    public void setStatusId(Long statusId) {
        status.setStatusId( (statusId!=null)?statusId:CREATED );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    @Override
    public String toString() {
        return id + "; " + taskName + "; " + info + "; ";
    }
}
