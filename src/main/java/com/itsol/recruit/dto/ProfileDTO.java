package com.itsol.recruit.dto;

import com.itsol.recruit.entity.AcademicLevel;
import com.itsol.recruit.entity.User;
import lombok.Data;

@Data
public class ProfileDTO {

    Long id;

    Long userId;

    String skills;

    int numberYearExperience;

    Long academicLevelId;

    String desiredSalary;

    String desiredWorkingAddress;

    String desiredWorkingForm;

}
