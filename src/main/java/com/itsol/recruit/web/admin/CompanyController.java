package com.itsol.recruit.web.admin;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.CompanyDTO;
import com.itsol.recruit.entity.Company;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.CompanyRepository;
import com.itsol.recruit.service.CompanyService;
import com.itsol.recruit.web.vm.PageVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constants.Api.Path.ADMIN)
@CrossOrigin
public class CompanyController {
    private CompanyService companyService;
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }
    @GetMapping(value = "/company/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(companyService.findCompanyById(id));
    }

    @GetMapping(value = "/company")
    public ResponseEntity<Company> getCompanyByName(@PathVariable("name") String name){
        return ResponseEntity.ok().body(companyService.findCompanyByName(name));
    }
    @PutMapping("/update-company")
    public ResponseEntity<Object> updateCompany(@RequestBody Company company) {
        System.out.println(company);
        return ResponseEntity.ok().body(companyService.updateCompany(company));
    }
}
