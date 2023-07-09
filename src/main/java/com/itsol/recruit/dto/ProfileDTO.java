package com.itsol.recruit.dto;

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

    String hometown;

    String gender;

    String birthDay;
}
