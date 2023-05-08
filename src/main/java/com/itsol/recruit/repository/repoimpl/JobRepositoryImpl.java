package com.itsol.recruit.repository.repoimpl;

import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.dto.request.JobSearchRequest;
import com.itsol.recruit.dto.respone.JobSearchResponse;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.BaseRepository;
import com.itsol.recruit.web.vm.JobVM;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JobRepositoryImpl extends BaseRepository {

    private final Logger log = LoggerFactory.getLogger(JobRepositoryImpl.class);
    public List<User> getAllByRole(List<Role> role) {
        String sql = "SELECT * from users u join permisstion p on u.id=p.user_id " +
                "WHERE p.role_id=?";
        List<User> users = getJdbcTemplate().query(sql, new Object[]{role.get(0).getId()}, new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    public List<JobDTO> seachUser(JobVM jobVM) {
        String query = "SELECT * FROM\n" +
                "(\n" +
                "    SELECT a.*, rownum r__\n" +
                "    FROM\n" +
                "    (\n" +
                "        SELECT * FROM JOB J JOIN JOB_POSITION JB ON J.job_position_id=JB.id JOIN STATUS_JOB S ON S.ID=J.status_id  WHERE UPPER(J.name) LIKE '%" + jobVM.getName().toUpperCase() + "%' " +
                " AND UPPER(S.code) LIKE '%" + jobVM.getStatusJob().toUpperCase() + "%'\n" +
                "         AND UPPER(JB.code)  LIKE '%" + jobVM.getJobPosition().toUpperCase() + "%' ORDER BY " +jobVM.getSortBy() + " " + jobVM.getSortDir()+"\n"+
                "    ) a\n" +
                "    WHERE rownum < (((" + jobVM.getPageNo() + "+1) * " + jobVM.getPageSize() + ") + 1 )\n" +
                ")\n" +
                "WHERE r__ >= ((" + jobVM.getPageNo() + " * " + jobVM.getPageSize() + ") + 1)";
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(JobDTO.class));

    }

}
