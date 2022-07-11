package com.itsol.recruit.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "academic_level")
@Data
public class AcademicLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_AL_ID")
    @SequenceGenerator(name = "GEN_AL_ID", sequenceName = "SEQ_AL", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String code;

    private String description;

    @Column(name = "is_delete",nullable = false)
    private boolean isDelete;
}
