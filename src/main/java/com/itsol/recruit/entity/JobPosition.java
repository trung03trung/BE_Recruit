package com.itsol.recruit.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "job_position")
@Data
public class JobPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_JOBP_ID")
    @SequenceGenerator(name = "GEN_JOBP_ID", sequenceName = "SEQ_JOBP", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String code;

    private String description;

    @Column(name = "is_delete",nullable = false)
    private boolean isDelete;

}
