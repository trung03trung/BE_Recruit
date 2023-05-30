package com.itsol.recruit.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class JobRegisterRequest {
    String descriptionYourself;
    MultipartFile cvFile;

    Long jobId;
}
