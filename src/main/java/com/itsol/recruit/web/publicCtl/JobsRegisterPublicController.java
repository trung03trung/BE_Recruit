package com.itsol.recruit.web.publicCtl;

import com.itsol.recruit.core.Constants;


import com.itsol.recruit.dto.ResponseDTO;

import com.itsol.recruit.dto.request.JobRegisterRequest;
import com.itsol.recruit.file_util.FileUploadUtil;
import com.itsol.recruit.service.FilePdfService;
import com.itsol.recruit.service.JobsRegisterService;
import com.itsol.recruit.web.vm.FilePdfVM;
import com.itsol.recruit.web.vm.JobRegisterPublicVM;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
public class JobsRegisterPublicController {
    private final JobsRegisterService jobsRegisterService;

    private final FilePdfService filePdfService;

    public JobsRegisterPublicController(JobsRegisterService jobsRegisterService, FilePdfService filePdfService) {
        this.jobsRegisterService = jobsRegisterService;
        this.filePdfService = filePdfService;
    }

    @PostMapping("/register-job-public")
    public ResponseEntity<ResponseDTO> registerJobPublic(JobRegisterRequest request) throws IOException {
        return ResponseEntity.ok().body(jobsRegisterService.addJobRegister(request));
    }

}
