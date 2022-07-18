package com.itsol.recruit.service;

import com.itsol.recruit.dto.CompanyDTO;
import com.itsol.recruit.dto.ResonCompanyDTO;
import com.itsol.recruit.entity.Company;
import com.itsol.recruit.web.vm.PageVM;
import org.springframework.data.domain.Page;

public interface CompanyService {
    Page<ResonCompanyDTO> getCompanyByPage(PageVM pageVM, String search, String sortBy);

    Company addCompany(CompanyDTO companyDTO);
}
