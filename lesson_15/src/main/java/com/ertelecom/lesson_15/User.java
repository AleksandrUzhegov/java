package com.ertelecom.lesson_15;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;
    @Column(name="user_name")
    private String userName;

    @Override
    public String toString() {
        return userId + "; " + userName;
    }
}

