package com.itsol.recruit.entity;



import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.Nullable;

import javax.persistence.*;
@Table(name="file_pdf")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilePdf {
    @Id
    @Column(name = "file_name")
    String file_name;

    @Column(name = "download_url")
    String download_uri;

    @Column(name = "size_url", nullable = false)
    Long size_url;

    @Nullable
    @Lob
    @Column(length = 1000000)
    private byte[] data;
}
