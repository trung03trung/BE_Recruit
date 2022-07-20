package com.itsol.recruit.web.publicCtl;

import com.itsol.recruit.dto.NotificationDTO;
import com.itsol.recruit.entity.Notifications;
import com.itsol.recruit.service.NotificationService;
import io.swagger.annotations.Api;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "chat")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WebsocketController {
    private final NotificationService notificationService;

    public WebsocketController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @MessageMapping("/sendNotification")
    @SendTo("/topic/send-notification")
    public Notifications saveNotifications(NotificationDTO notificationDTO){
        return notificationService.save(notificationDTO);
    }
}
