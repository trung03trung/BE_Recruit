package com.itsol.recruit.repository.repoimpl;

import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.dto.JobsRegisterDTO;
import com.itsol.recruit.repository.BaseRepository;
import com.itsol.recruit.web.vm.JobsRegisterVM;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobsRegisterRepositoryImpl extends BaseRepository {
    public List<JobsRegisterDTO> seachJobsRegister(JobsRegisterVM jobsRegisterVM) {
        String query = "SELECT * FROM\n" +
                "(\n" +
                "    SELECT a.*, rownum r__\n" +
                "    FROM\n" +
                "    (\n" +
                "     SELECT * FROM JOBS_REGISTER JR JOIN JOB J ON JR.job_id=J.id JOIN USERS U ON U.id=JR.user_id JOIN STATUS_JOB_REGISTER S ON S.id=JR.status_id " +
                "WHERE UPPER(J.name) LIKE '%" + jobsRegisterVM.getName().toUpperCase() + "%' or UPPER(U.user_name) LIKE '%" + jobsRegisterVM.getName().toUpperCase() + "%'\n" +
                "         OR UPPER(S.code)  LIKE '%" + jobsRegisterVM.getName().toUpperCase() + "%' ORDER BY JR.id DESC \n" +
                "    ) a\n" +
                "    WHERE rownum < (((" + jobsRegisterVM.getPageNo() + "+1) * " + jobsRegisterVM.getPageSize() + ") + 1 )\n" +
                ")\n" +
                "WHERE r__ >= ((" + jobsRegisterVM.getPageNo() + " * " + jobsRegisterVM.getPageSize() + ") + 1)" +
                "ORDER BY " + jobsRegisterVM.getSortBy() + " " + jobsRegisterVM.getSortDir();
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(JobsRegisterDTO.class));
    }
}
