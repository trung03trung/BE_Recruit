package com.itsol.recruit.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "job")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_JOB_ID")
    @SequenceGenerator(name = "GEN_JOB_ID", sequenceName = "SEQ_JOB", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "job_position_id",nullable = false)
    private JobPosition jobPosition;

    @Column(name = "number_experience",nullable = false)
    private String numberExperience;

    @ManyToOne
    @JoinColumn(name = "working_form_id",nullable = false)
    private WorkingForm workingForm;

    @Column(name = "address_work",nullable = false)
    private String addressWork;

    @ManyToOne
    @JoinColumn(name = "academic_level_id",nullable = false)
    private AcademicLevel academicLevel;

    @ManyToOne
    @JoinColumn(name = "rank_id",nullable = false)
    private Rank rank;

    @Column(name = "qtyPerson",nullable = false)
    private int qtyPerson;

    @Column(name = "start_recruitment_date",nullable = false)
    private Date startDate;

    @Column(name = "due_date",nullable = false)
    private Date dueDate;

    @Column(nullable = false)
    private String skills;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String interrest;

    @Column(name = "job_requirement",nullable = false)
    private String jobRequirement;

    @Column(name = "salary_min",nullable = false)
    private int salaryMin;

    @Column(name = "salary_max",nullable = false)
    private int salaryMax;

    @ManyToOne
    @JoinColumn(name = "contact_id",nullable = false)
    private User userContact;


    @OneToOne
    @JoinColumn(name = "create_id",nullable = false)
    private User  userCreate;

    @Column(name = "create_date",nullable = false)
    private Date createdDate;

    @ManyToOne()
    @JoinColumn(name = "update_id")
    private User userUpdate;

    @Column(name = "update_date")
    private Date updateDate;

    @ManyToOne
    @JoinColumn(name = "status_id",nullable = false)
    private StatusJob statusJob;

    @Column(nullable = false)
    private int views;

    @Column(name = "is_delete",nullable = false)
    private boolean isDelete;

    private String reason;


}
