package com.itsol.recruit.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Date;
@Data
public class StatisticalDTO {
    @Nullable
    Number all_job;
    @Nullable
    Number total_view_job;
    @Nullable
    Number waiting_for_interview;
    @Nullable
    Number interviewing;
    @Nullable
    Number total_apply;
    @Nullable
    Number success_recruited_applicant;
}
