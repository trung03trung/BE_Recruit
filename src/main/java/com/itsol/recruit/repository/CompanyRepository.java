package com.itsol.recruit.repository;

import com.itsol.recruit.dto.CompanyDTO;
import com.itsol.recruit.entity.Company;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findById(long id);
}
