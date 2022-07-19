package com.itsol.recruit.dto;

import com.itsol.recruit.entity.Job;
import com.itsol.recruit.entity.TypeNotifications;
import com.itsol.recruit.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class NotificationDTO {

    @NotNull
    Long userSenderId;

    @NotNull
    Long userReceiverId;

    @NotNull
    Date createDate;

    Long jobId;

    @NotNull
    Long typeNotificationsId;

    boolean isDelete;
}
