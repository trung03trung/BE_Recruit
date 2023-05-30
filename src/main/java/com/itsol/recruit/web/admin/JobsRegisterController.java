package com.itsol.recruit.web.admin;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.JobsRegisterDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.JobsRegister;
import com.itsol.recruit.entity.Profile;
import com.itsol.recruit.service.JobsRegisterService;
import com.itsol.recruit.web.vm.JobVM;
import com.itsol.recruit.web.vm.JobsRegisterVM;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/job-register/{id}")
    public ResponseEntity<JobsRegister> getJobById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(jobsRegisterService.getById(id));
    }


    @GetMapping(value = "/job-register/profile/{id}")
    public ResponseEntity<Profile> getProfileByJobsRegister(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(jobsRegisterService.getProfileByJobRegister(id));
    }

    @PostMapping(value = "/job-register/{id}")
    public ResponseEntity<ResponseDTO> changeStatusJob(@PathVariable("id") Long id, @RequestParam("code") String code){
        try {
            ResponseDTO responseDTO=jobsRegisterService.changeStatus(id,code);
            responseDTO.setStatus(HttpStatus.OK);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,"Sever Error"));
        }
    }

    @PostMapping(value = "/profile-reject/{id}")
    public ResponseEntity<ResponseDTO> rejectStatusJob(@PathVariable("id") Long id,
                                                       @RequestParam("code") String code,
                                                       @RequestParam("reason") String reason){
        try {
            ResponseDTO responseDTO=jobsRegisterService.rejectStatus(id,code,reason);
            responseDTO.setStatus(HttpStatus.OK);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,"Sever Error"));
        }
    }

    @PostMapping(value = "job-register/interview")
    public ResponseEntity<ResponseDTO> sendEmailInterview(@RequestBody JobsRegisterDTO jobsRegisterDTO){
        try{
            ResponseDTO responseDTO=jobsRegisterService.scheduleInterview(jobsRegisterDTO);
            responseDTO.setStatus(HttpStatus.OK);
            return ResponseEntity.ok().body(responseDTO);
        }catch (IllegalAccessError e){
            e.printStackTrace();
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.BAD_REQUEST,"Send email interview fail"));
        }
    }

    @PostMapping(value = "job-register/search")
    public ResponseEntity<JobsRegisterVM> searchJobsRegister(@RequestBody JobsRegisterVM jobsRegisterVM){
        return ResponseEntity.ok().body(jobsRegisterService.searchJobRegister(jobsRegisterVM));
    }
    @GetMapping("/job-register/download-cv/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = jobsRegisterService.downloadCv(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
