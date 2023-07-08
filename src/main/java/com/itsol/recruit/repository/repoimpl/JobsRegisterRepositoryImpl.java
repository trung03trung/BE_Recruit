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
                "     SELEcCT * FROM JOBS_REGISTER JR JOIN JOB J ON JR.job_id=J.id JOIN USERS U ON U.id=JR.user_id JOIN STATUS_JOB_REGISTER S ON S.id=JR.status_id " +
                "WHERE UPPER(J.name) LIKE '%" + jobsRegisterVM.getJobName().toUpperCase() + "%' AND UPPER(U.user_name) LIKE '%" + jobsRegisterVM.getApplyName().toUpperCase() + "%'\n" +
                "         AND S.id  LIKE '%" + jobsRegisterVM.getStatus() + "%' ORDER BY " + jobsRegisterVM.getSortBy() + " " + jobsRegisterVM.getSortDir()+" \n"+
                "    ) a\n" +
                "    WHERE rownum < (((" + jobsRegisterVM.getPageNo() + "+1) * " + jobsRegisterVM.getPageSize() + ") + 1 )\n" +
                ")\n" +
                "WHERE r__ >= ((" + jobsRegisterVM.getPageNo() + " * " + jobsRegisterVM.getPageSize() + ") + 1)";
//        String queryTotal= "SELECT count(R.id) FROM  tbl_control_method R WHERE UPPER(R.code) LIKE '%" + .getCode().toUpperCase().trim() + "%' " +
//                " AND UPPER(R.name) LIKE '%" + controlMethodVM.getName().toUpperCase().trim() + "%'\n" +
//                "         AND UPPER(R.status)  LIKE '" + controlMethodVM.getStatus().toUpperCase() + "%'";
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(JobsRegisterDTO.class));
    }
}
