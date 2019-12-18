package com.ertelecom.gwt.common;

public class UserDto {
    private Long userId;
    private String userName;
    private String password;

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

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || !(obj instanceof UserDto)) return false;
        if (this == obj) return true;
        return (userId!=null) && userId.equals( ((UserDto)obj).userId );
    }

}

