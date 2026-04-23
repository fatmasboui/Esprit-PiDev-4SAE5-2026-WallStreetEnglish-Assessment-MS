package com.example.notification.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins ="*")
public class TestNotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public TestNotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/api/notify/test")
    public String testNotification() {
        messagingTemplate.convertAndSend("/topic/notifications", "Salut Fatima ! Notification test !");
        return "Notification envoyée !";
    }
}
