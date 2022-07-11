package com.itsol.recruit.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "status_job")
@Data
public class StatusJob {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_ST_ID")
    @SequenceGenerator(name = "GEN_ST_ID", sequenceName = "SEQ_ST", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String code;

    private String description;

    @Column(name = "is_delete")
    private boolean isDelete;
}
