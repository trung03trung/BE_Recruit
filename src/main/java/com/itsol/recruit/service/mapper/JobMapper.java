package com.itsol.recruit.service.mapper;

import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.repository.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobMapper implements EntityMapper<JobDTO,Job>{

    private final JobPositionRepository jobPositionRepository;

    private final RankRepository rankRepository;

    private final StatusJobRepository statusJobRepository;

    private final WorkingFormRepository workingFormRepository;

    private final AcademicLevelRepository academicLevelRepository;

    private final UserRepository userRepository;


    public JobMapper(JobPositionRepository jobPositionRepository, RankRepository rankRepository, StatusJobRepository statusJobRepository, WorkingFormRepository workingFormRepository, AcademicLevelRepository academicLevelRepository, UserRepository userRepository) {
        this.jobPositionRepository = jobPositionRepository;
        this.rankRepository = rankRepository;
        this.statusJobRepository = statusJobRepository;
        this.workingFormRepository = workingFormRepository;
        this.academicLevelRepository = academicLevelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Job toEntity(JobDTO dto) {
        if (dto == null) {
            return null;
        }

        Job entity = new Job();
        BeanUtils.copyProperties(dto, entity);
        entity.setJobPosition(jobPositionRepository.findJobPositionById(dto.getJobPositionId()));
        entity.setAcademicLevel(academicLevelRepository.findAcademicLevelById(dto.getAcademicLevelId()));
        entity.setRank(rankRepository.findRankById(dto.getRankId()));
        entity.setWorkingForm(workingFormRepository.findWorkingFormById(dto.getWorkingFormId()));
        entity.setUserContact(userRepository.findUserById(dto.getUserContactId()));
        entity.setUserCreate(userRepository.findByUserName(dto.getUserCreate()));
        entity.setUserUpdate(userRepository.findUserById(dto.getUserUpdateId()));
        return entity;
    }

    @Override
    public JobDTO toDto(Job entity) {
        if (entity == null) {
            return null;
        }

        JobDTO dto = new JobDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setRankId(entity.getRank().getId());
        dto.setAcademicLevelId(entity.getAcademicLevel().getId());
        dto.setJobPositionId(entity.getJobPosition().getId());
        dto.setWorkingFormId(entity.getWorkingForm().getId());
        dto.setUserContactId(entity.getUserContact().getId());
        dto.setUserCreate(entity.getUserCreate().getUserName());
        dto.setUserUpdateId(entity.getUserUpdate().getId());
        return dto;

    }

    @Override
    public List<Job> toEntity(List<JobDTO> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> toDto(List<Job> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
