package com.itsol.recruit.web.vm;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

@Data
public class FilePdfVM {
    private Long id;
    private String file_name;
    private String download_uri;
    private Long size_url;
    private byte[] data;
}
