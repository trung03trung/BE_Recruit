package com.itsol.recruit.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
public class JobsRegisterDTO {
    Long id;

    Date dateInterview;

    String methodInterview;

    String mediaType;

    LocalTime timeInterview;
}
