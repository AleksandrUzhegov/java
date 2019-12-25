package com.ertelecom.server;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JwtAuthRequest {
    private String username;
    private String password;

    public JwtAuthRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}