package com.itsol.recruit.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;
import javax.persistence.*;

@Entity(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Profile {

    @Id
    @Column()
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILES_SEQ")
    @SequenceGenerator(name = "PROFILES_SEQ", sequenceName = "PROFILES_SEQ", allocationSize = 1, initialValue = 1)
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
    String desiredWorkingForm;

    @Column(name = "is_delete",nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDelete;
}
