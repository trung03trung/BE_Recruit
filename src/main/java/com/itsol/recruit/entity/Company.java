package com.itsol.recruit.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity(name ="company")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMPANY_SEQ")
    @SequenceGenerator(name = "COMPANY_SEQ", sequenceName = "COMPANY_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "hotline", nullable = false)
    String hotLine;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_incoporation", nullable = false)
    Date dateIncoporation;

    @Column(name = "tax_code", nullable = false)
    String taxCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tax_date", nullable = false)
    Date taxDate;

    @Column(name = "tax_place", nullable = false)
    String taxPlace;

    @Column(name = "head_office", nullable = false)
    String headOffice;

    @Column(name = "number_staff", nullable = false)
    String numberStaff ;

    @Column(name = "link_web", nullable = false)
    String  linkWeb ;

    @Column(name = "description", nullable = false)
    String  description ;

    @Column(name = "avatar", nullable = false)
    String  avatar ;

    @Column(name = "backdrop_img", nullable = false)
    String  backdropImg ;

    @Column(name = "is_delete")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDelete;

}