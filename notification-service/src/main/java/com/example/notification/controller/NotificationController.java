package com.example.notification.controller;

import com.example.notification.model.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins ="*")
public class NotificationController {

    @MessageMapping("/send/quiz")
    @SendTo("/topic/quiz")
    public Notification sendQuizNotification(Notification notification) {
        return notification;
    }

    @MessageMapping("/send/career")
    @SendTo("/topic/career")
    public Notification sendCareerNotification(Notification notification) {
        return notification;
    }

    @MessageMapping("/send/certification")
    @SendTo("/topic/certification")
    public Notification sendCertificationNotification(Notification notification) {
        return notification;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/notifications")
    public Notification sendGeneralNotification(Notification notification) {
        return notification;
    }
}
