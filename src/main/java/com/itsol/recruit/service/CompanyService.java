package com.itsol.recruit.service;

import com.itsol.recruit.dto.CompanyDTO;
import com.itsol.recruit.dto.ReasonCompanyDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.request.CompanyRequest;
import com.itsol.recruit.entity.Company;
import com.itsol.recruit.web.vm.PageVM;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface CompanyService {
    public Company findCompanyById(long id);
    public Company findCompanyByName(String name);
    public ResponseEntity<ResponseDTO> createCompany(CompanyRequest request) throws IOException;
}
