package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.JobsRegisterDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.*;
import com.itsol.recruit.repository.JobsRegisterRepository;
import com.itsol.recruit.repository.StatusJobRegisterRepository;
import com.itsol.recruit.repository.repoimpl.ProfileRepositoryImpl;
import com.itsol.recruit.service.JobsRegisterService;
import com.itsol.recruit.service.email.EmailService;
import com.itsol.recruit.web.vm.JobsRegisterVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Service
public class JobsRegisterServiceImpl implements JobsRegisterService {

    private final JobsRegisterRepository jobsRegisterRepository;

    private final ProfileRepositoryImpl profileRepositoryImpl;

    private final StatusJobRegisterRepository statusJobRegisterRepository;

    private final EmailService emailService;

    public JobsRegisterServiceImpl(JobsRegisterRepository jobsRegisterRepository, ProfileRepositoryImpl profileRepositoryImpl, StatusJobRegisterRepository statusJobRegisterRepository, EmailService emailService) {
        this.jobsRegisterRepository = jobsRegisterRepository;
        this.profileRepositoryImpl = profileRepositoryImpl;
        this.statusJobRegisterRepository = statusJobRegisterRepository;
        this.emailService = emailService;
    }

    @Override
    public JobsRegisterVM getAllJobsRegister(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<JobsRegister> jobsRegisters= jobsRegisterRepository.findAll(pageable);
        JobsRegisterVM jobsRegisterVM=new JobsRegisterVM(jobsRegisters.getContent(),jobsRegisters.getNumber(),jobsRegisters.getSize(),jobsRegisters.getTotalElements(),
                jobsRegisters.getTotalPages(),jobsRegisters.isLast());
        return jobsRegisterVM;
    }

    @Override
    public JobsRegister getById(Long id) {
        return jobsRegisterRepository.findJobsRegisterById(id);
    }
    public Profile getProfileByJobRegister(Long id){
        JobsRegister jobsRegister=jobsRegisterRepository.findJobsRegisterById(id);
        return profileRepositoryImpl.getProfileByUser(jobsRegister.getUser());
    }

    @Override
    public ResponseDTO changeStatus(Long id, String code) {
        JobsRegister jobsRegister=jobsRegisterRepository.findJobsRegisterById(id);
        StatusJobRegister statusJobRegister=statusJobRegisterRepository.findStatusJobRepositoryByCode(code);
        jobsRegister.setStatusJobRegister(statusJobRegister);
        jobsRegisterRepository.save(jobsRegister);
        return new ResponseDTO("Change status success");
    }

    @Override
    public ResponseDTO rejectStatus(Long id, String code, String reason) {
        JobsRegister jobsRegister=jobsRegisterRepository.findJobsRegisterById(id);
        StatusJobRegister statusJobRegister=statusJobRegisterRepository.findStatusJobRepositoryByCode(code);
        jobsRegister.setStatusJobRegister(statusJobRegister);
        jobsRegister.setReason(reason);
        jobsRegisterRepository.save(jobsRegister);
        return new ResponseDTO("Change status success");
    }

    @Override
    public ResponseDTO scheduleInterview(JobsRegisterDTO jobsRegisterDTO) throws IllegalAccessError {
        JobsRegister jobsRegister=jobsRegisterRepository.findJobsRegisterById(jobsRegisterDTO.getId());
        StatusJobRegister statusJobRegister=statusJobRegisterRepository.findStatusJobRepositoryByCode("Đang phỏng vấn");
        jobsRegister.setStatusJobRegister(statusJobRegister);
        jobsRegister.setDateInterview(jobsRegisterDTO.getDateInterview());
        jobsRegister.setMethodInterview(jobsRegisterDTO.getMethodInterview());
        jobsRegister.setMediaType(jobsRegisterDTO.getMediaType());
        DateTimeFormatter formattime= DateTimeFormatter.ofPattern("HH:mm");
        SimpleDateFormat formatdate=new SimpleDateFormat("dd/MM/yyyy");
        String date=formatdate.format(jobsRegister.getDateInterview());
        String time=formattime.format(jobsRegisterDTO.getTimeInterview());
        String emails= emailService.buildMailInterview(jobsRegister.getJob().getName(),
                time,date,jobsRegister.getMediaType(),jobsRegister.getJob().getUserContact().getName(),
                jobsRegister.getJob().getUserContact().getPhoneNumber(),jobsRegister.getUser().getUserName());
        emailService.sendEmail(jobsRegister.getUser().getEmail(),emails);
        jobsRegisterRepository.save(jobsRegister);
        return new ResponseDTO("Send email interview success");
    }
}
