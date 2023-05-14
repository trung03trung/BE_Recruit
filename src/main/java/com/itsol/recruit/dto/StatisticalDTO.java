package com.itsol.recruit.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Date;
@Data
public class StatisticalDTO {
    @Nullable
    Integer all_job;
    @Nullable
    Integer total_view_job;
    @Nullable
    Integer waiting_for_interview;
    @Nullable
    Integer interviewing;
    @Nullable
    Integer total_apply;
    @Nullable
    Integer success_recruited_applicant;
    @Nullable
    Integer false_applicant;
}
