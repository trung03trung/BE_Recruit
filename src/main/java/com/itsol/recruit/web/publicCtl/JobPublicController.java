package com.itsol.recruit.web.publicCtl;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.service.JobService;
import com.itsol.recruit.service.impl.PDFGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
public class JobPublicController {

    private final JobService jobService;

    public JobPublicController(JobService jobService, PDFGenerator pdfGenerator) {
        this.jobService = jobService;
    }

    @GetMapping(value = "/job/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(jobService.getJobById(id));
    }

    @GetMapping(value = "/get-all-job-publick")
    public ResponseEntity<List<Job>> getAllJobPublic(){
        return ResponseEntity.ok().body(jobService.getAllJobPublic());
    }
}
