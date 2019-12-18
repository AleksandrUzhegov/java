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

    public Long getStatusId() {
        return status.getStatusId();
    }

    public void setStatusId(Long statusId) {
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

    public String getStatusName() {
        if (status != null) return status.getStatusName();
        return "";
    }

    @Override
    public String toString() {
        return id + "; " + taskName + "; " + getOwnerName() + "; " + getExecutorName() + "; " + info + "; " + getStatusName();
    }

    public Task(TaskDto taskDto) {
        init();
        this.id = taskDto.getId();
        this.taskName = taskDto.getTaskName();
        this.info = taskDto.getInfo();
        this.status = new Status(taskDto.getStatus() );
        this.owner = new User(taskDto.getOwner());
        this.executor = new User(taskDto.getExecutor());
    }

    public TaskDto getTaskDto() {
        TaskDto taskDto = new TaskDto(id, taskName, info, null, null, null);
        taskDto.setStatus( status.getStatusDto() );
        if (owner!=null) taskDto.setOwner( owner.getUserDto() );
        if (executor!=null) taskDto.setExecutor( executor.getUserDto() );
        return taskDto;
    }
}
