package com.itsol.recruit.repository.repoimpl;

import com.itsol.recruit.dto.StatisticalDTO;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.BaseRepository;
import com.itsol.recruit.repository.repoext.UserRepositoryExt;
import com.itsol.recruit.web.vm.SeachVM;
import com.itsol.recruit.web.vm.StatisticalVm;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class UserRepositoryImpl extends BaseRepository implements UserRepositoryExt {
    @Override
    public User getAllUser() {
        return null;
    }

    public List<User> seachUser(SeachVM seachVM) {
        String query = "SELECT * FROM\n" +
                "(\n" +
                "    SELECT a.*, rownum r__\n" +
                "    FROM\n" +
                "    (\n" +
                "        SELECT USERS.ID, USERS.NAME, USERS.USER_NAME, USERS.EMAIL, USERS.HOME_TOWN, USERS.GENDER, USERS.BIRTH_DAY, USERS.ACTIVATE, USERS.IS_DELETE, USERS.PHONE_NUMBER,USERS.AVATAR FROM USERS JOIN PERMISSTION ON USERS.ID = PERMISSTION.USER_ID\n" +
                "                  JOIN ROLES ON ROLES.ID = PERMISSTION.ROLE_ID WHERE ROLES.ID = 2 and name LIKE '%" + seachVM.getName() + "%' and user_name LIKE '%" + seachVM.getUserName() + "%' and email LIKE '%" + seachVM.getEmail() + "%'\n" +
                "        ORDER BY id DESC\n" +
                "    ) a\n" +
                "    WHERE rownum < ((" + seachVM.getPageNumber() + " * " + seachVM.getPageSize() + ") + 1 )\n" +
                ")\n" +
                "WHERE r__ >= (((" + seachVM.getPageNumber() + "-1) * " + seachVM.getPageSize() + ") + 1)" +
                "ORDER BY " + seachVM.getSortColum() + " " + seachVM.getSortT();
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(User.class));
    }


    public List<StatisticalDTO> StatisticalData(StatisticalVm statisticalDTO) {
        String strS = statisticalDTO.getDatestart();
        String strE = statisticalDTO.getDateend();
        System.out.println(statisticalDTO.getDatestart());
        System.out.println(statisticalDTO.getDateend());
        String query = "WITH time_filtered_job AS\n" +
                "  (SELECT *\n" +
                "   FROM job\n" +
                "   WHERE start_recruitment_date BETWEEN to_date("+strS+", 'DDMMYYYY') AND to_date("+strE+", 'DDMMYYYY') ),\n" +
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
                "   AND ps.code = 'P_SUCCESS'),\n" +
                "     false_applicant AS\n" +
                "  (SELECT count(jr.job_id) false_applicant\n" +
                "   FROM time_filtered_job j\n" +
                "   INNER JOIN jobs_register jr ON jr.job_id = j.id\n" +
                "   INNER JOIN status_job_register ps ON ps.id = jr.status_id\n" +
                "   AND ps.code = 'false')\n" +
                "SELECT all_job,\n" +
                "       nvl(total_view_job, 0) total_view_job,\n" +
                "       waiting_for_interview,\n" +
                "       interviewing,\n" +
                "       total_apply,\n" +
                "       success_recruited_applicant,\n" +
                "       false_applicant\n" +
                "FROM all_job,\n" +
                "     total_view_job,\n" +
                "     waiting_for_interview,\n" +
                "     interviewing,\n" +
                "     total_apply,\n" +
                "     success_recruited_applicant,\n" +
                "     false_applicant";
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(StatisticalDTO.class));
    }
}

