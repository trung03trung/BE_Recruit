package com.itsol.recruit.dto;

import com.itsol.recruit.entity.JobPosition;
import com.itsol.recruit.entity.WorkingForm;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Data
public class JobDTO {

    Long id;

    @NotNull
    Long jobPositionId;

    @NotEmpty
    @Size(max=200)
    String name;

    @NotEmpty
    @Size(max=300)
    String numberExperience;

    @NotNull
    Long workingFormId;

    @NotEmpty
    @Size(max=200)
    String addressWork;

    @NotNull
    Long academicLevelId;

    @NotNull
    Long rankId;

    @NotNull
    int qtyPerson;


    @NotNull
    Date dueDate;

    @NotNull
    Date startDate;

    @NotEmpty
    String skills;

    @NotEmpty
    @Size(max = 2000)
    String description;

    @NotEmpty
    @Size(max = 2000)
    String interest;

    @NotEmpty
    @Size(max = 2000)
    String jobRequirement;

    @NotNull
    int salaryMax;

    @NotNull
    int salaryMin;

    @NotNull
    Long userContactId;

    @NotNull
    String userCreate;

    Long userUpdateId;


}
