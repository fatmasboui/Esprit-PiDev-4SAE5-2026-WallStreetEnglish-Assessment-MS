package com.example.notification.dto;

import lombok.Data;

@Data
public class NotificationRequest {

    private String message;
    private String sender;
    private String type;
}
