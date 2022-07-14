package com.itsol.recruit.web.admin;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.service.JobsRegisterService;
import com.itsol.recruit.web.vm.JobVM;
import com.itsol.recruit.web.vm.JobsRegisterVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.Api.Path.ADMIN)
public class JobsRegisterController {
    private final JobsRegisterService jobsRegisterService;

    public JobsRegisterController(JobsRegisterService jobsRegisterService) {
        this.jobsRegisterService = jobsRegisterService;
    }

    @GetMapping(value = "/job-register")
    public ResponseEntity<JobsRegisterVM> getAllJobRegister(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "dateRegister",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir ){
        return ResponseEntity.ok().body(jobsRegisterService.getAllJobsRegister(pageNo,pageSize,sortBy,sortDir));
    }
}
