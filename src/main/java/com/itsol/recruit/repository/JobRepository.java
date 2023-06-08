package com.itsol.recruit.repository;

import com.itsol.recruit.entity.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JobRepository extends PagingAndSortingRepository<Job,Long> {
    Job findJobById(Long id);

    @Query(value = "select count(u) from job u where u.dueDate between :SEVEN_DAY_BEFORE_CURRENT_DATE and CURRENT_DATE")
    Long findTotalJobDueDate(@Param("SEVEN_DAY_BEFORE_CURRENT_DATE") Date Date);

}
