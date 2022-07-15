package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.*;
import com.itsol.recruit.repository.JobsRegisterRepository;
import com.itsol.recruit.repository.StatusJobRegisterRepository;
import com.itsol.recruit.repository.repoimpl.ProfileRepositoryImpl;
import com.itsol.recruit.service.JobsRegisterService;
import com.itsol.recruit.web.vm.JobsRegisterVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class JobsRegisterServiceImpl implements JobsRegisterService {

    private final JobsRegisterRepository jobsRegisterRepository;

    private final ProfileRepositoryImpl profileRepositoryImpl;

    private final StatusJobRegisterRepository statusJobRegisterRepository;

    public JobsRegisterServiceImpl(JobsRegisterRepository jobsRegisterRepository, ProfileRepositoryImpl profileRepositoryImpl, StatusJobRegisterRepository statusJobRegisterRepository) {
        this.jobsRegisterRepository = jobsRegisterRepository;
        this.profileRepositoryImpl = profileRepositoryImpl;
        this.statusJobRegisterRepository = statusJobRegisterRepository;
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
}
