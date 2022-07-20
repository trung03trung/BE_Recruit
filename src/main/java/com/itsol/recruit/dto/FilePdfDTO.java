package com.itsol.recruit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilePdfDTO {
    @NonNull
    Long id;

    @NonNull
    @Size(max = 200)
    String file_name;

    @NonNull
    @Size(max = 200)
    String download_uri;

    @NonNull
    Long size_url;
}
