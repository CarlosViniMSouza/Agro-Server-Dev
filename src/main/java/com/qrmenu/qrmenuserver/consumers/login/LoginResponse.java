package com.qrmenu.qrmenuserver.consumers.login;

import lombok.Data;

@Data
public class LoginResponse {
    private boolean success;
    private String message;
}
