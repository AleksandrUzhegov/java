package com.ertelecom.gwt.common;

public class StatusDto {
    private Long statusId;
    private String statusName;

    public StatusDto() {
    }

    public StatusDto(Long statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj!=null) && (statusId == ((StatusDto)obj).statusId);
    }
}
