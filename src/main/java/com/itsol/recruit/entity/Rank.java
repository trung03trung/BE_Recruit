package com.itsol.recruit.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GEN_RANK_ID")
    @SequenceGenerator(name = "GEN_RANK_ID", sequenceName = "SEQ_RANK", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String code;

    private String description;

    @Column(name = "is_delete")
    private boolean isDelete;

}
