package com.itsol.recruit.dto;

import lombok.Data;

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
