package com.itsol.recruit.repository.repoimpl;

import com.itsol.recruit.dto.StatisticalDTO;
import com.itsol.recruit.entity.Role;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.BaseRepository;
import com.itsol.recruit.web.vm.StatisticalVm;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class JobRepositoryImpl extends BaseRepository {
    public List<User> getAllByRole(List<Role> role){
        String sql="SELECT * from users u join permisstion p on u.id=p.user_id " +
                "WHERE p.role_id=?";
        List<User> users=getJdbcTemplate().query(sql,new Object[]{role.get(0).getId()},new BeanPropertyRowMapper<>(User.class));
        return users;
    }

    public List<StatisticalDTO> StatisticalData(StatisticalVm statisticalDTO){
        String query = "WITH time_filtered_job AS\n" +
                "  (SELECT *\n" +
                "   FROM job),\n" +
                "     all_job AS\n" +
                "  (SELECT count(j.id) all_job\n" +
                "   FROM job j\n" +
                "   JOIN status_job js ON j.status_id = js.id),\n" +
                "     total_view_job AS\n" +
                "  (SELECT sum(VIEWS) total_view_job\n" +
                "   FROM time_filtered_job),\n" +
                "     waiting_for_interview AS\n" +
                "  (SELECT count(jr.job_id) waiting_for_interview\n" +
                "   FROM time_filtered_job j\n" +
                "   INNER JOIN jobs_register jr ON jr.job_id = j.id\n" +
                "   INNER JOIN status_job_register ps ON ps.id = jr.status_id\n" +
                "   AND ps.code = 'P_INTERVIEW'),\n" +
                "     interviewing AS\n" +
                "  (SELECT count(jr.job_id) interviewing\n" +
                "   FROM time_filtered_job j\n" +
                "   INNER JOIN jobs_register jr ON jr.job_id = j.id\n" +
                "   INNER JOIN status_job_register ps ON ps.id = jr.status_id\n" +
                "   AND ps.code = 'P_APPROVED'),\n" +
                "     total_apply AS\n" +
                "  (SELECT count(*) total_apply\n" +
                "   FROM time_filtered_job j\n" +
                "   LEFT JOIN jobs_register jr ON jr.job_id = j.id),\n" +
                "     success_recruited_applicant AS\n" +
                "  (SELECT count(jr.job_id) success_recruited_applicant\n" +
                "   FROM time_filtered_job j\n" +
                "   INNER JOIN jobs_register jr ON jr.job_id = j.id\n" +
                "   INNER JOIN status_job_register ps ON ps.id = jr.status_id\n" +
                "   AND ps.code = 'P_SUCCESS')\n" +
                "SELECT all_job,\n" +
                "       nvl(total_view_job, 0) total_view_job,\n" +
                "       waiting_for_interview,\n" +
                "       interviewing,\n" +
                "       total_apply,\n" +
                "       success_recruited_applicant\n" +
                "FROM all_job,\n" +
                "     total_view_job,\n" +
                "     waiting_for_interview,\n" +
                "     interviewing,\n" +
                "     total_apply,\n" +
                "     success_recruited_applicant";
       return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(StatisticalDTO.class));
    }
}
