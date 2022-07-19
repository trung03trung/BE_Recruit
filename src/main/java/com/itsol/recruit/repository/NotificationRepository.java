package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notifications,Long> {

}
