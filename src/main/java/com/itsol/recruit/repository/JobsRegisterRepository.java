package com.itsol.recruit.repository;

import com.itsol.recruit.entity.JobsRegister;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JobsRegisterRepository extends PagingAndSortingRepository<JobsRegister,Long> {

    JobsRegister findJobsRegisterById(Long id);

}
