package com.qrmenu.qrmenuserver.consumers;

import lombok.Data;

@Data
public class ConsumerService {
    private String message;

    public ConsumerService(String message) {
        super();
        this.message = message;
    }
}
