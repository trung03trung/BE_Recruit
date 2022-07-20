package com.itsol.recruit.entity;



import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
@Table(name="file_pdf")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilePdf {
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_PDF_SEQ")
    @SequenceGenerator(name = "FILE_PDF_SEQ", sequenceName = "FILE_PDF_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Id
    @Column(name = "file_name")
    String file_name;

    @Column(name = "download_url")
    String download_uri;

    @Column(name = "size_url", nullable = false)
    Long size_url;

    @Lob
    @Column(length = 1000000)
    private byte[] data;
}
