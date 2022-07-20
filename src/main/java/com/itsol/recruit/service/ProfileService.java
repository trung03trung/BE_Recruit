package com.itsol.recruit.service;

import com.itsol.recruit.entity.AcademicLevel;
import com.itsol.recruit.repository.AcademicLevelRepository;

import java.util.List;

public class ProfileService {
    private final AcademicLevelRepository academicLevelRepository;

    public ProfileService(AcademicLevelRepository academicLevelRepository) {
        this.academicLevelRepository = academicLevelRepository;
    }

    public List<AcademicLevel> getAll(){
        return academicLevelRepository.findAll();
    }
}
