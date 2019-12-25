package com.ertelecom.server;

import com.ertelecom.gwt.common.StatusDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "statuses")
@Data
public class Status {
    @Id
    @Column(name="status_id")
    private Long statusId;
    @Column(name="status_name")
    private String statusName;

    @Override
    public String toString() {
        return statusId + "; " + statusName;
    }

    public Status() {
    }

    public Status(StatusDto statusDto) {
        this.statusId = statusDto.getStatusId();
        this.statusName = statusDto.getStatusName();
    }

    public StatusDto getStatusDto() {
        return new StatusDto(statusId, statusName);
    }

}
