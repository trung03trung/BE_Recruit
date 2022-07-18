package com.itsol.recruit.web.vm;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class JobRegisterPublicVM {
    @Nullable
    private String userName;
    @Nullable
    private String pdf;
    @Nullable
    private Long jobId;
    @Nullable
    private String code;
    @Nullable
    private String media_type;
}
