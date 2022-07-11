package com.itsol.recruit.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "working_form")
@Data
public class WorkingForm {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_WK_ID")
    @SequenceGenerator(name = "GEN_WK_ID", sequenceName = "SEQ_WK", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String code;

    private String description;

    @Column(name = "is_delete")
    private boolean isDelete;

}
