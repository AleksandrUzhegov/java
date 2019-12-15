package com.ertelecom.server;

import com.ertelecom.gwt.common.UserDto;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @Column(name="user_name")
    private String userName;

    public User() {
    }

    public User(UserDto userDto) {
        this.userId = userDto.getUserId();
        this.userName = userDto.getUserName();
    }

    public UserDto getUserDto() {
        return new UserDto(userId, userName);
    }

    @Override
    public String toString() {
        return userId + "; " + userName;
    }
}

