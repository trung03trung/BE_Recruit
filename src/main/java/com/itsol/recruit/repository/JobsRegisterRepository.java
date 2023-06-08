package com.itsol.recruit.repository;

import com.itsol.recruit.entity.JobsRegister;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface JobsRegisterRepository extends PagingAndSortingRepository<JobsRegister,Long> {

    JobsRegister findJobsRegisterById(Long id);

    @Query(value = "select count(u.id) from jobs_register u where u.statusJobRegister.id = :status and u.dateRegister <= :current_date")
    Long findByDateAndStatus(@Param("current_date") Date currentDate, @Param("status") Long statusId);
}
