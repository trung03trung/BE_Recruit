package com.itsol.recruit.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "jobs_register")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobsRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_JOBSREGISTER_ID")
    @SequenceGenerator(name = "GEN_JOBSREGISTER_ID", sequenceName = "SEQ_JOBSREGISTER", allocationSize = 1)
    Long id;

    @ManyToOne
    @JoinColumn(name = "job_id",nullable = false)
    Job job;

    @OneToOne()
    @JoinColumn(name = "user_id",nullable = false)
    User user;

    @Column(name = "date_register",nullable = false)
    Date dateRegister;

    @Column(name = "date_interview")
    Date dateInterview;

    @Column(name = "method_interview")
    String methodInterview;

    @Column(name = "address_interview")
    String addressInterview;

    @ManyToOne()
    @JoinColumn(name = "status_id",nullable = false)
    StatusJobRegister statusJobRegister;

    String reason;

    @OneToOne
    @JoinColumn(name="cv_file")
    FilePdf cv_file;

    @Column(name = "media_type",nullable = false)
    String mediaType;

    @Column(name = "is_delete ")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDelete ;

}
