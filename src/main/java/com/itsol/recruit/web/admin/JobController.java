package com.itsol.recruit.web.admin;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.request.JobSearchRequest;
import com.itsol.recruit.dto.respone.JobSearchResponse;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.service.JobService;
import com.itsol.recruit.service.impl.PDFGenerator;
import com.itsol.recruit.web.vm.JobFieldVM;
import com.itsol.recruit.web.vm.JobVM;
import com.itsol.recruit.web.vm.StatisticalVm;
import com.lowagie.text.DocumentException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.ADMIN)
@CrossOrigin("*")
public class JobController {

    private final JobService jobService;

    private final PDFGenerator pdfGenerator;
    public JobController(JobService jobService, PDFGenerator pdfGenerator) {
        this.jobService = jobService;
        this.pdfGenerator = pdfGenerator;
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
    public ResponseEntity<Job> creatNewJob( @RequestBody JobDTO jobDTO){
        return ResponseEntity.ok().body(jobService.createNewJob(jobDTO));
    }

    @GetMapping(value = "/select")
    public ResponseEntity<JobFieldVM> getAllFieldSelect(){
        return ResponseEntity.ok().body(jobService.getAllFieldSelect());
    }

    @PutMapping(value = "/job")
    public ResponseEntity<Job> updateJob(@Valid @RequestBody JobDTO jobDTO){
        return ResponseEntity.ok().body(jobService.createNewJob(jobDTO));
    }

    @GetMapping(value = "/pdf/{id}")
    public void exportToPDF(HttpServletResponse response,@PathVariable("id")Long id,
                            @RequestParam("token")String token) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        response.addHeader("Authorization","Bearer "+token);
        pdfGenerator.export(response,id);

    }

    @PostMapping(value = "/job/{id}")
    public ResponseEntity<ResponseDTO> changeStatusJob(@PathVariable("id") Long id, @RequestParam("code") String code){
        try {
            ResponseDTO responseDTO=jobService.changeStatus(id,code);
            responseDTO.setStatus(HttpStatus.OK);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,"Sever Error"));
        }
    }

    @PostMapping(value = "/job-reject/{id}")
    public ResponseEntity<ResponseDTO> rejectStatusJob(@PathVariable("id") Long id,
                                                       @RequestParam("code") String code,
                                                       @RequestParam("reason") String reason){
        try {
            ResponseDTO responseDTO=jobService.rejectStatus(id,code,reason);
            responseDTO.setStatus(HttpStatus.OK);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,"Sever Error"));
        }
    }

    @DeleteMapping(value = "/job/{id}")
    public ResponseEntity<ResponseDTO> deleteJob(@PathVariable("id") Long id){
        try {
            ResponseDTO responseDTO=jobService.deleteJobById(id);
            responseDTO.setStatus(HttpStatus.OK);
            return ResponseEntity.ok().body(responseDTO);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR,"Sever Error"));
        }
    }

    @PostMapping(value = "/job/search")
    public ResponseEntity<JobVM> searchJob(@RequestBody JobVM jobVM){
        return ResponseEntity.ok().body(jobService.searchJob(jobVM));
    }

    @GetMapping(value = "/job/export-data")
    public ResponseEntity<Resource> exportData() throws IOException {
        ByteArrayResource byteArrayResource = new ByteArrayResource(jobService.exportData());
        HttpHeaders headers = new HttpHeaders();
        List<String> customHeaders = new ArrayList<>();
        customHeaders.add("Content-Disposition");
        customHeaders.add("Content-Response");
        headers.setAccessControlExposeHeaders(customHeaders);
        headers.set("Content-Disposition", "attachment;filename=" + "FILE_EXPORT.xlsx");
        headers.set("Content-Response", "FILE_EXPORT.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(byteArrayResource);
    }

    @PostMapping(value = "/job/export-dashboard")
    public ResponseEntity<Resource> exportDataDashboard(@RequestBody StatisticalVm request) throws IOException {
        ByteArrayResource byteArrayResource = new ByteArrayResource(jobService.exportDataDashboard(request));
        HttpHeaders headers = new HttpHeaders();
        List<String> customHeaders = new ArrayList<>();
        customHeaders.add("Content-Disposition");
        customHeaders.add("Content-Response");
        headers.setAccessControlExposeHeaders(customHeaders);
        headers.set("Content-Disposition", "attachment;filename=" + "FILE_EXPORT_DASHBOARD.xlsx");
        headers.set("Content-Response", "FILE_EXPORT_DASHBOARD.xlsx");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(byteArrayResource);
    }
}
