package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.StatisticalDTO;
import com.itsol.recruit.entity.*;
import com.itsol.recruit.repository.*;
import com.itsol.recruit.repository.repoimpl.JobRepositoryImpl;
import com.itsol.recruit.service.JobService;
import com.itsol.recruit.service.mapper.JobMapper;
import com.itsol.recruit.web.vm.JobFieldVM;
import com.itsol.recruit.web.vm.JobVM;
import com.itsol.recruit.web.vm.StatisticalVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    private final JobPositionRepository jobPositionRepository;

    private final RankRepository rankRepository;

    private final StatusJobRepository statusJobRepository;

    private final WorkingFormRepository workingFormRepository;

    private final AcademicLevelRepository academicLevelRepository;


    private final RoleRepository roleRepository;

    private final JobRepositoryImpl jobRepositoryimpl;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper, JobPositionRepository jobPositionRepository, RankRepository rankRepository, StatusJobRepository statusJobRepository, WorkingFormRepository workingFormRepository, AcademicLevelRepository academicLevelRepository, RoleRepository roleRepository, RoleRepository roleRepository1, JobRepositoryImpl jobRepositoryimpl) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.jobPositionRepository = jobPositionRepository;
        this.rankRepository = rankRepository;
        this.statusJobRepository = statusJobRepository;
        this.workingFormRepository = workingFormRepository;
        this.academicLevelRepository = academicLevelRepository;
        this.roleRepository = roleRepository1;
        this.jobRepositoryimpl = jobRepositoryimpl;
    }


    @Override
    public  JobVM getAllJob(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Job> jobs=jobRepository.findAll(pageable);
        JobVM jobVM=new JobVM(jobs.getContent(),jobs.getNumber(),jobs.getSize(),jobs.getTotalElements(),
                jobs.getTotalPages(),jobs.isLast());
        return jobVM;
    }

    @Override
    public Job createNewJob(JobDTO jobDTO) {
        Job job=jobMapper.toEntity(jobDTO);
        job.setCreatedDate(new Date());
        job.setUpdateDate(new Date());
        StatusJob statusJob=statusJobRepository.findStatusJobById((long)1);
        job.setStatusJob(statusJob);
        return jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findJobById(id);
    }

    @Override
    public JobFieldVM getAllFieldSelect() {
        List<AcademicLevel> academicLevels=academicLevelRepository.findAll();
        List<Rank> ranks=rankRepository.findAll();
        List<JobPosition> jobPositions=jobPositionRepository.findAll();
        List<WorkingForm> workingForms=workingFormRepository.findAll();
        List<Role> role=roleRepository.findByCode("ROLE_JE");
        List<User> users=jobRepositoryimpl.getAllByRole(role);
        return new JobFieldVM(academicLevels,jobPositions,ranks,workingForms,users);
    }

    @Override
    public ResponseDTO changeStatus(Long id, String code) {
        Job job=jobRepository.findJobById(id);
        StatusJob statusJob=statusJobRepository.findStatusJobByCode(code);
        job.setStatusJob(statusJob);
        jobRepository.save(job);
        return new ResponseDTO("Change status success");
    }

}
