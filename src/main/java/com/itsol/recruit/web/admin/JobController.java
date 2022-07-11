package com.itsol.recruit.web.admin;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.service.JobService;
import com.itsol.recruit.web.vm.JobFieldVM;
import com.itsol.recruit.web.vm.JobVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.ADMIN)
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping(value = "/job")
    public ResponseEntity<JobVM> getAllJob(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "dueDate",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir ){
        return ResponseEntity.ok().body(jobService.getAllJob(pageNo,pageSize,sortBy,sortDir));
    }

    @GetMapping(value = "/job/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(jobService.getJobById(id));
    }

    @PostMapping(value = "/job")
    public ResponseEntity<Job> creatNewJob(@Valid @RequestBody JobDTO jobDTO){
        return ResponseEntity.ok().body(jobService.createNewJob(jobDTO));
    }

    @GetMapping(value = "/select")
    public ResponseEntity<JobFieldVM> getAllFieldSelect(){
        return ResponseEntity.ok().body(jobService.getAllFieldSelect());
    }

}
