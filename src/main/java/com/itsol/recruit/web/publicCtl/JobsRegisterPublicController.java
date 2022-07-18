package com.itsol.recruit.web.publicCtl;

import com.itsol.recruit.core.Constants;


import com.itsol.recruit.dto.ResponseDTO;

import com.itsol.recruit.service.JobsRegisterService;
import com.itsol.recruit.web.vm.JobRegisterPublicVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
public class JobsRegisterPublicController {
    private final JobsRegisterService jobsRegisterService;


    public JobsRegisterPublicController(JobsRegisterService jobsRegisterService) {
        this.jobsRegisterService = jobsRegisterService;
    }

//    @PostMapping(value = "userSeach")
//    public ResponseEntity<List<User>> seachUser(@RequestBody SeachVM seachVM) {
//        return ResponseEntity.ok().body(userService.seachUser(seachVM));
//    }

    @PostMapping("/register-job-public")
    public ResponseEntity<ResponseEntity<ResponseDTO>> registerJobPublic(@RequestBody JobRegisterPublicVM jobRegisterPublic) {
        return ResponseEntity.ok().body(jobsRegisterService.addJobRegis(jobRegisterPublic));
    }
}
