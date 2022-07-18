package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.*;
import com.itsol.recruit.repository.JobRepository;
import com.itsol.recruit.repository.JobsRegisterRepository;
import com.itsol.recruit.repository.StatusJobRegisterRepository;
import com.itsol.recruit.repository.UserRepository;
import com.itsol.recruit.repository.repoimpl.ProfileRepositoryImpl;
import com.itsol.recruit.service.JobsRegisterService;
import com.itsol.recruit.web.vm.JobRegisterPublicVM;
import com.itsol.recruit.web.vm.JobsRegisterVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JobsRegisterServiceImpl implements JobsRegisterService {

    private final JobsRegisterRepository jobsRegisterRepository;

    private final ProfileRepositoryImpl profileRepositoryImpl;

    private final StatusJobRegisterRepository statusJobRegisterRepository;

    private final UserRepository userRepository;

    private final JobRepository jobRepository;

    public JobsRegisterServiceImpl(JobsRegisterRepository jobsRegisterRepository, ProfileRepositoryImpl profileRepositoryImpl, StatusJobRegisterRepository statusJobRegisterRepository, UserRepository userRepository, JobRepository jobRepository) {
        this.jobsRegisterRepository = jobsRegisterRepository;
        this.profileRepositoryImpl = profileRepositoryImpl;
        this.statusJobRegisterRepository = statusJobRegisterRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
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
        StatusJobRegister statusJobRegister = statusJobRegisterRepository.findStatusJobRepositoryByCode(code);
        jobsRegister.setStatusJobRegister(statusJobRegister);
        jobsRegisterRepository.save(jobsRegister);
        return new ResponseDTO("Change status success");
    }

    @Override
    public ResponseDTO rejectStatus(Long id, String code, String reason) {
        JobsRegister jobsRegister = jobsRegisterRepository.findJobsRegisterById(id);
        StatusJobRegister statusJobRegister = statusJobRegisterRepository.findStatusJobRepositoryByCode(code);
        jobsRegister.setStatusJobRegister(statusJobRegister);
        jobsRegister.setReason(reason);
        jobsRegisterRepository.save(jobsRegister);
        return new ResponseDTO("Change status success");
    }

    @Override
    public ResponseEntity<ResponseDTO> addJobRegis(JobRegisterPublicVM jobRegisterPublicVM) {
        Job job = jobRepository.findJobById(jobRegisterPublicVM.getJobId());
        User user = userRepository.findByUserName(jobRegisterPublicVM.getUserName());
        if (job == null || user == null) {
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.NOT_FOUND, "NOT_FOUND"));
        }
        /* if(user.getPhoneNumber() == null || user.getEmail()== null){

        }*/
        StatusJobRegister statusJobRegister = statusJobRegisterRepository.findStatusJobRepositoryByCode("Chờ xét duyệt");
        JobsRegister jobsRegister = new JobsRegister();
        jobsRegister.setJob(job);
        jobsRegister.setUser(user);
        jobsRegister.setDateRegister(new Date());
        jobsRegister.setStatusJobRegister(statusJobRegister);
        jobsRegister.setDelete(false);
        /*jobsRegister.setDateInterview(new Date());*/
        jobsRegister.setReason(jobRegisterPublicVM.getCode());
        jobsRegister.setMediaType(jobRegisterPublicVM.getMedia_type());
        jobsRegister.setCvFile(jobRegisterPublicVM.getPdf());
        jobsRegisterRepository.save(jobsRegister);
        return ResponseEntity.ok().body(
                new ResponseDTO(HttpStatus.OK, "ok"));
    }

}
