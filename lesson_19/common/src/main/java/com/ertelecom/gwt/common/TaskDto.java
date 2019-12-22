package com.ertelecom.gwt.common;

public class TaskDto {
    public static final long CREATED = 1;
    private Long id;
    private String taskName;
    private String info;
    private StatusDto status;
    private UserDto owner;
    private UserDto executor;

    private void init() {
        status = new StatusDto();
        owner = new UserDto();
        executor = new UserDto();
        setStatusId(CREATED);
    }

    public TaskDto() {
        init();
    }

    public TaskDto(Long id, String taskName, String info, Long statusId, Long ownerId, Long executorId) {
        init();
        this.id = id;
        this.taskName = taskName;
        this.info = info;
        this.setStatusId(statusId);
        this.setOwnerId(ownerId);
        this.setExecutorId(executorId);
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

    public void setStatus(StatusDto status) {
        this.status = status;
    }

    public StatusDto getStatus() {
        return status;
    }

    public void setStatusId(Long statusId) {
        status.setStatusId( (statusId!=null)?statusId:CREATED );
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public void setOwnerId(Long ownerId) {
        owner.setUserId(ownerId);
    }

    public UserDto getExecutor() {
        return executor;
    }

    public void setExecutor(UserDto executor) {
        this.executor = executor;
    }

    public void setExecutorId(Long executorId) {
        executor.setUserId(executorId);
    }

}
