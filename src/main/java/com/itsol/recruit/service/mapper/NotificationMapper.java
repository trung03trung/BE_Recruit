package com.itsol.recruit.service.mapper;

import com.itsol.recruit.dto.JobsRegisterDTO;
import com.itsol.recruit.dto.NotificationDTO;
import com.itsol.recruit.entity.Notifications;
import com.itsol.recruit.repository.JobRepository;
import com.itsol.recruit.repository.TypeNotificationRepository;
import com.itsol.recruit.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationMapper implements EntityMapper<NotificationDTO, Notifications> {

    private final UserRepository userRepository;

    private final JobRepository jobRepository;

    private final TypeNotificationRepository typeNotificationRepository;

    public NotificationMapper(UserRepository userRepository, JobRepository jobRepository, TypeNotificationRepository typeNotificationRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.typeNotificationRepository = typeNotificationRepository;
    }

    @Override
    public Notifications toEntity(NotificationDTO dto) {
        if (dto == null) {
            return null;
        }

        Notifications entity = new Notifications();
        BeanUtils.copyProperties(dto, entity);
        entity.setUserSender(userRepository.findUserById(dto.getUserSenderId()));
        entity.setUserReceiver(userRepository.findUserById(dto.getUserReceiverId()));
        entity.setJob(jobRepository.findJobById(dto.getJobId()));
        entity.setTypeNotifications(typeNotificationRepository.findTypeNotificationsById(dto.getTypeNotificationsId()));
        return entity;
    }

    @Override
    public NotificationDTO toDto(Notifications entity) {
        if (entity == null) {
            return null;
        }

        NotificationDTO dto = new NotificationDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setUserReceiverId(entity.getUserReceiver().getId());
        dto.setUserSenderId(entity.getUserSender().getId());
        dto.setJobId(entity.getJob().getId());
        dto.setTypeNotificationsId(entity.getTypeNotifications().getId());
        return dto;
    }

    @Override
    public List<Notifications> toEntity(List<NotificationDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDTO> toDto(List<Notifications> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
