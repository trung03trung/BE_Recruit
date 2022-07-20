package com.itsol.recruit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
;

@Entity
@Table(name = "image")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {

    Long userId;

    @Id
    @Column(name = "name")
    String name;

    @Column(name = "type")
    String type;

    @Column(name = "image", unique = false, nullable = false, length = 100000)
    byte[] image;
}
