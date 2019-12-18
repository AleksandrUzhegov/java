package com.ertelecom.lesson_15;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "statuses")
@Data
public class Status {
    @Id
    @Column(name="status_id")
    private long statusId;
    @Column(name="status_name")
    private String statusName;

    @Override
    public String toString() {
        return statusId + "; " + statusName;
    }

}
