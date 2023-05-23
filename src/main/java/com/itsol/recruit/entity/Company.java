package com.itsol.recruit.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity(name ="company")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_incoporation", nullable = false)
    Date dateIncoporation;

    @Column(name = "head_office", nullable = false)
    String headOffice;

    @Column(name = "number_staff", nullable = false)
    String numberStaff ;

    @Column(name = "link_web", nullable = false)
    String  linkWeb ;

    @Column(name = "description", nullable = false)
    String  description ;

    @Column(name = "avatar", nullable = false)
    String  avatar;

    @Column(name = "backdrop_img", nullable = false)
    String  backdropImg ;

    @Column(name = "is_delete")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isDelete;
}