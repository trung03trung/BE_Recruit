package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.CompanyDTO;
import com.itsol.recruit.dto.ReasonCompanyDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.Company;
import com.itsol.recruit.repository.CompanyRepository;
import com.itsol.recruit.service.CompanyService;
import com.itsol.recruit.web.vm.PageVM;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company findCompanyById(long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company findCompanyByName(String name) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDTO> updateCompany(Company company) {
        Optional<Company> findCompany = companyRepository.findById(company.getId());
        if(findCompany !=null) {
            companyRepository.save(company);
            return ResponseEntity.ok().body(
                    new ResponseDTO(HttpStatus.OK, "OK"));
        }
        return ResponseEntity.ok().body(
                new ResponseDTO(HttpStatus.NOT_FOUND, "NOT_FOUND"));
    }
}
