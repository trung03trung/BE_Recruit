package com.itsol.recruit.service.impl;

import com.itsol.recruit.entity.JobsRegister;
import com.itsol.recruit.repository.JobsRegisterRepository;
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

    public JobsRegisterServiceImpl(JobsRegisterRepository jobsRegisterRepository) {
        this.jobsRegisterRepository = jobsRegisterRepository;
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
}
