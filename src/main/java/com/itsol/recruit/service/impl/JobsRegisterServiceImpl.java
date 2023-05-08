package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.JobsRegisterDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.*;
import com.itsol.recruit.repository.*;
import com.itsol.recruit.repository.repoimpl.ProfileRepositoryImpl;
import com.itsol.recruit.service.JobsRegisterService;
import com.itsol.recruit.service.email.EmailService;
import com.itsol.recruit.web.vm.JobRegisterPublicVM;
import com.itsol.recruit.web.vm.JobsRegisterVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Date;

@Service
public class JobsRegisterServiceImpl implements JobsRegisterService {

    private final JobsRegisterRepository jobsRegisterRepository;

    private final ProfileRepositoryImpl profileRepositoryImpl;

    private final StatusJobRegisterRepository statusJobRegisterRepository;

    private final UserRepository userRepository;

    private final JobRepository jobRepository;
    private final FilePdfRepository filePdfRepository;

    private final EmailService emailService;

    public JobsRegisterServiceImpl(JobsRegisterRepository jobsRegisterRepository, ProfileRepositoryImpl profileRepositoryImpl, StatusJobRegisterRepository statusJobRegisterRepository, UserRepository userRepository, JobRepository jobRepository, FilePdfRepository filePdfRepository,EmailService emailService) {
        this.jobsRegisterRepository = jobsRegisterRepository;
        this.profileRepositoryImpl = profileRepositoryImpl;
        this.statusJobRegisterRepository = statusJobRegisterRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.filePdfRepository = filePdfRepository;
        this.emailService = emailService;
    }

    @Override
    public JobsRegisterVM getAllJobsRegister(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<JobsRegister> jobsRegisters = jobsRegisterRepository.findAll(pageable);
        JobsRegisterVM jobsRegisterVM = new JobsRegisterVM(jobsRegisters.getContent(), jobsRegisters.getNumber(), jobsRegisters.getSize(), jobsRegisters.getTotalElements(),
                jobsRegisters.getTotalPages(), jobsRegisters.isLast());
        return jobsRegisterVM;
    }

    @Override
    public JobsRegister getById(Long id) {
        return jobsRegisterRepository.findJobsRegisterById(id);
    }

    public Profile getProfileByJobRegister(Long id) {
        JobsRegister jobsRegister = jobsRegisterRepository.findJobsRegisterById(id);
        return profileRepositoryImpl.getProfileByUser(jobsRegister.getUser());
    }

    @Override
    public ResponseDTO changeStatus(Long id, String code) {
        JobsRegister jobsRegister = jobsRegisterRepository.findJobsRegisterById(id);
        StatusJobRegister statusJobRegister = statusJobRegisterRepository.findStatusJobRegisterByCode(code);
        jobsRegister.setStatusJobRegister(statusJobRegister);
        jobsRegisterRepository.save(jobsRegister);
        return new ResponseDTO("Change status success");
    }

    @Override
    public ResponseDTO rejectStatus(Long id, String code, String reason) {
        JobsRegister jobsRegister = jobsRegisterRepository.findJobsRegisterById(id);
        StatusJobRegister statusJobRegister = statusJobRegisterRepository.findStatusJobRegisterByCode(code);
        jobsRegister.setStatusJobRegister(statusJobRegister);
        jobsRegister.setReason(reason);
        jobsRegisterRepository.save(jobsRegister);
        return new ResponseDTO("Change status success");
    }

    @Override
    public ResponseDTO scheduleInterview(JobsRegisterDTO jobsRegisterDTO) {
        JobsRegister jobsRegister = jobsRegisterRepository.findJobsRegisterById(jobsRegisterDTO.getId());
        jobsRegister.setStatusJobRegister(statusJobRegisterRepository.findStatusJobRegisterByCode("Đang phỏng vấn"));
        jobsRegister.setMethodInterview(jobsRegister.getMethodInterview());
        jobsRegister.setDateInterview(jobsRegister.getDateInterview());
        Job job = jobRepository.findJobById(jobsRegister.getJob().getId());
        String email = emailService.buildMailInterview(job.getName(),jobsRegisterDTO.getTimeInterview().toString(),jobsRegisterDTO.getDateInterview().toString(),
                jobsRegisterDTO.getMethodInterview(),job.getUserContact().getName(),job.getUserContact().getPhoneNumber(),jobsRegister.getUser().getUserName());
        jobsRegisterRepository.save(jobsRegister);
        ResponseDTO response = new ResponseDTO();
        response.setCode("Success");
        return response;
    }

    @Override
    public JobsRegisterVM searchJobRegister(JobsRegisterVM jobsRegisterVM) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO> addJobRegister(JobRegisterPublicVM jobRegisterPublicVM)  {
        Job job = jobRepository.findJobById(jobRegisterPublicVM.getJobId());
        User user = userRepository.findByUserName(jobRegisterPublicVM.getUserName());
        System.out.println(job);
        System.out.println(user);
        if (job == null || user == null) {
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.NOT_FOUND, "NOT_FOUND"));
        }
        if (ObjectUtils.isEmpty(jobRegisterPublicVM.getPdf()) || jobRegisterPublicVM.getPdf().isEmpty()) {
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.BAD_REQUEST, "BAD_REQUEST"));
        }
        FilePdf filePdf = new FilePdf();
        filePdf.setFile_name(jobRegisterPublicVM.getPdf());
        filePdf.setSize_url(10L);
        filePdf.setDownload_uri("");
        filePdfRepository.save(filePdf);

        StatusJobRegister statusJobRegister = statusJobRegisterRepository.findStatusJobRegisterByCode("Chờ duyệt");
        JobsRegister jobsRegister = new JobsRegister();
        jobsRegister.setJob(job);
        jobsRegister.setUser(user);
        jobsRegister.setDateRegister(new Date());
        jobsRegister.setStatusJobRegister(statusJobRegister);
        jobsRegister.setDelete(false);
        jobsRegister.setReason("Lý Do");
        jobsRegister.setMediaType("Trực tiếp");
        jobsRegister.setCv_file(filePdf);
        jobsRegisterRepository.save(jobsRegister);

        return ResponseEntity.ok().body(
                new ResponseDTO(HttpStatus.OK, "ok"));
    }
}
