package com.itsol.recruit.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Entity(name = "job")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_SEQ")
    @SequenceGenerator(name = "PROFILE_SEQ", sequenceName = "PROFILE_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @OneToOne()
    @JoinColumn(name = "user_id",nullable = false)
    User user;

    String skills;

    @Column(name =  "number_year_experience")
    int numberYearExperience;

    @ManyToOne()
    @JoinColumn(name = "academic_name_id")
    AcademicLevel academicLevel;

    @Column(name = "desired_salary")
    String desiredSalary;

    @Column(name = "desired_working_address")
    String desiredWorkingAddress;

    @Column(name = "desired_working_form")
    String desiredWorkingform;

    @Column(name = "is_delete ")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDelete   ;
}
