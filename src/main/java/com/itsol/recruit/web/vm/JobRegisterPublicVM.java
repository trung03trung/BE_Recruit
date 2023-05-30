package com.itsol.recruit.web.vm;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Data
public class JobRegisterPublicVM {

    private String pdf;

    private Long jobId;
}
