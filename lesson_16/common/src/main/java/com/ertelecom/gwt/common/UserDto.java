package com.ertelecom.gwt.common;

public class UserDto {
    private Long userId;
    private String userName;

    public UserDto() {
    }

    public UserDto(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj!=null) && (userId == ((UserDto)obj).userId);
    }

}
