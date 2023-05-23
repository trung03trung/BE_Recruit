package com.itsol.recruit.web.publicCtl;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.respone.JobDetailResponse;
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
    public ResponseEntity<JobDetailResponse> getJobById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(jobService.getJobDetailById(id));
    }

    @GetMapping(value = "/get-job")
    public ResponseEntity<List<Job>> getAllJobPublic(@RequestParam(name = "type") String type){
        return ResponseEntity.ok().body(jobService.getJobPublic(type));
    }
}
