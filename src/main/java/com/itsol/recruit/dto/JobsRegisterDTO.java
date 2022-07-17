package com.itsol.recruit.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
public class JobsRegisterDTO {
    Long id;

    Long jobId;

    Long userId;

    Date dateRegister;

    Date dateInterview;

    String methodInterview;

    String addressInterview;

    String mediaType;

    Long statusId;

    String reason;

    String cvFile;

    LocalTime timeInterview;

    boolean isDelete;
}
