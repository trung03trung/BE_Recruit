package com.itsol.recruit.service.mapper;

import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.dto.JobsRegisterDTO;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.entity.JobsRegister;
import com.itsol.recruit.repository.JobRepository;
import com.itsol.recruit.repository.StatusJobRegisterRepository;
import com.itsol.recruit.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobsRegisterMapper implements EntityMapper<JobsRegisterDTO, JobsRegister>{

    private final JobRepository jobRepository;

    private final UserRepository userRepository;

    private final StatusJobRegisterRepository statusJobRegisterRepository;

    public JobsRegisterMapper(JobRepository jobRepository, UserRepository userRepository, StatusJobRegisterRepository statusJobRegisterRepository) {
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.statusJobRegisterRepository = statusJobRegisterRepository;
    }

    @Override
    public JobsRegister toEntity(JobsRegisterDTO dto) {
        if (dto == null) {
            return null;
        }

        JobsRegister entity = new JobsRegister();
        BeanUtils.copyProperties(dto, entity);
        entity.setJob(jobRepository.findJobById(dto.getJobId()));
        entity.setUser(userRepository.findUserById(dto.getUserId()));
        entity.setStatusJobRegister(statusJobRegisterRepository.findStatusJobRegisterById(dto.getStatusId()));
        return entity;
    }

    @Override
    public JobsRegisterDTO toDto(JobsRegister entity) {
        if (entity == null) {
            return null;
        }

        JobsRegisterDTO dto = new JobsRegisterDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setJobId(entity.getJob().getId());
        dto.setUserId(entity.getUser().getId());
        dto.setStatusId(entity.getStatusJobRegister().getId());
        return dto;
    }

    @Override
    public List<JobsRegister> toEntity(List<JobsRegisterDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<JobsRegisterDTO> toDto(List<JobsRegister> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
