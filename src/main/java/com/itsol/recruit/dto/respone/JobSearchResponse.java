package com.itsol.recruit.dto.respone;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class JobSearchResponse {
    Long id;

    String jobPosition;

    String name;


    Date dueDate;

    int salaryMax;

    int salaryMin;

    Long statusJob;

}
