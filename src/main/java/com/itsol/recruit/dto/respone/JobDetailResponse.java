package com.itsol.recruit.dto.respone;

import com.itsol.recruit.entity.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class JobDetailResponse {

    private Long id;

    private String name;

    private JobPosition jobPosition;

    private String numberExperience;


    private WorkingForm workingForm;

    private String addressWork;


    private AcademicLevel academicLevel;


    private Rank rank;


    private int qtyPerson;

    private Date startDate;

    private Date dueDate;

    private String skills;

    private List<String> description;

    private List<String> interrest;


    private List<String> jobRequirement;


    private int salaryMin;


    private int salaryMax;


    private StatusJob statusJob;


    private int views;

    private Company company;

}
