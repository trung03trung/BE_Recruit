package com.itsol.recruit.web.vm;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class JobRegisterPublicVM {
    private String userName;
    private String pdf;
    private Long jobId;
    @Nullable
    private String code;
}
