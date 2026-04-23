package com.example.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.example.notification.model.Notification;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(Notification notification) {
        notification.setTimestamp(LocalDateTime.now().toString());
        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
}
