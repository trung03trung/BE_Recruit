package com.itsol.recruit.service.impl;


import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.request.CompanyRequest;
import com.itsol.recruit.entity.Company;
import com.itsol.recruit.repository.CompanyRepository;
import com.itsol.recruit.service.CompanyService;


import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    public ResponseEntity<ResponseDTO> createCompany(CompanyRequest request) throws IOException {
        Company company = new Company();
        BeanUtils.copyProperties(request, company);
        if (request.getAvatar() != null) {
            byte[] imageArr = request.getAvatar().getBytes();
            String imageAsString = Base64.encodeBase64String(imageArr);
            company.setAvatar(imageAsString);
        }
        companyRepository.save(company);

        return ResponseEntity.ok().body(
                new ResponseDTO(HttpStatus.OK, "OK"));
    }
}
