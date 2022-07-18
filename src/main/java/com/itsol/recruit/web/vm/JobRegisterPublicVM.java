package com.itsol.recruit.web.vm;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Data
public class JobRegisterPublicVM {
    @Nullable
    private String userName;
    @Nullable
    private MultipartFile pdf;
    @Nullable
    private Long jobId;
    @Nullable
    private String code;
    @Nullable
    private String media_type;
}
