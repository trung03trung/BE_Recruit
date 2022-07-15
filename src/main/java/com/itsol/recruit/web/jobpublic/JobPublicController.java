package com.itsol.recruit.web.jobpublic;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.service.JobService;
import com.itsol.recruit.service.impl.PDFGenerator;
import com.itsol.recruit.web.vm.JobFieldVM;
import com.itsol.recruit.web.vm.JobVM;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.PUBLIC)
public class JobPublicController {

    private final JobService jobService;

    private final PDFGenerator pdfGenerator;
    public JobPublicController(JobService jobService, PDFGenerator pdfGenerator) {
        this.jobService = jobService;
        this.pdfGenerator = pdfGenerator;
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
