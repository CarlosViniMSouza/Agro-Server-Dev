package com.qrmenu.qrmenuserver.consumers.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
