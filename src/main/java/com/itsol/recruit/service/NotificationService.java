package com.itsol.recruit.service;

import com.itsol.recruit.dto.NotificationDTO;
import com.itsol.recruit.entity.Notifications;

public interface NotificationService {
    Notifications save(NotificationDTO notificationDTO);
}
