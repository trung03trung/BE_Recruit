package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.NotificationDTO;
import com.itsol.recruit.entity.Notifications;
import com.itsol.recruit.repository.NotificationRepository;
import com.itsol.recruit.service.NotificationService;
import com.itsol.recruit.service.mapper.NotificationMapper;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationMapper notificationMapper, NotificationRepository notificationRepository) {
        this.notificationMapper = notificationMapper;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Notifications save(NotificationDTO notificationDTO) {
        Notifications notifications=notificationMapper.toEntity(notificationDTO);
        notificationRepository.save(notifications);
        return notifications;
    }
}
