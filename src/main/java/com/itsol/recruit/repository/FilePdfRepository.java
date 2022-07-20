package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Company;
import com.itsol.recruit.entity.FilePdf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilePdfRepository extends JpaRepository<FilePdf, Long> {
}
