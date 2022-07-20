package com.itsol.recruit.repository;

import com.itsol.recruit.entity.TypeNotifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeNotificationRepository extends JpaRepository<TypeNotifications,Long> {
    TypeNotifications findTypeNotificationsById(Long id);
}
