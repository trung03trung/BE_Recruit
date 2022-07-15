package com.itsol.recruit.repository;

import com.itsol.recruit.entity.StatusJobRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusJobRegisterRepository extends JpaRepository<StatusJobRegister,Long> {

    StatusJobRegister findStatusJobRepositoryByCode(String code);
}
