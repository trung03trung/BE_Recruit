package com.itsol.recruit.repository.repoimpl;

import com.itsol.recruit.dto.StatisticalDTO;
import com.itsol.recruit.dto.respone.ColumnChartResponse;
import com.itsol.recruit.dto.respone.LineChartDataResponse;
import com.itsol.recruit.entity.User;
import com.itsol.recruit.repository.BaseRepository;
import com.itsol.recruit.repository.repoext.UserRepositoryExt;
import com.itsol.recruit.web.vm.SeachVM;
import com.itsol.recruit.web.vm.StatisticalVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepositoryImpl extends BaseRepository implements UserRepositoryExt {

    private final Logger log = LoggerFactory.getLogger(UserRepositoryImpl.class);
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
                "                  JOIN ROLES ON ROLES.ID = PERMISSTION.ROLE_ID WHERE ROLES.ID = 3 and name LIKE '%" + seachVM.getName() + "%' and user_name LIKE '%" + seachVM.getUserName() + "%' and email LIKE '%" + seachVM.getEmail() + "%'\n" +
                "        ORDER BY id DESC\n" +
                "    ) a\n" +
                "    WHERE rownum < ((" + seachVM.getPageNumber() + " * " + seachVM.getPageSize() + ") + 1 )\n" +
                ")\n" +
                "WHERE r__ >= (((" + seachVM.getPageNumber() + "-1) * " + seachVM.getPageSize() + ") + 1)" +
                "ORDER BY " + seachVM.getSortColum() + " " + seachVM.getSortT();
        System.out.println(getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(User.class)));
        return getJdbcTemplate().query(query, new BeanPropertyRowMapper<>(User.class));
    }


    public List<StatisticalDTO> StatisticalData(StatisticalVm statisticalVm) {
        String strS = statisticalVm.getDatestart();
        String strE = statisticalVm.getDateend();
        System.out.println(statisticalVm.getDatestart());
        System.out.println(statisticalVm.getDateend());
        String query = "WITH time_filtered_job AS\n" +
                "  (SELECT *\n" +
                "   FROM job\n" +
                "   WHERE start_recruitment_date BETWEEN to_date('"+strS+"', 'YYYY-MM-DD') AND to_date('"+strE+"', 'YYYY-MM-DD') ),\n" +
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
                "   AND ps.code = 'Chờ phỏng vấn'),\n" +
                "     interviewing AS\n" +
                "  (SELECT count(jr.job_id) interviewing\n" +
                "   FROM time_filtered_job j\n" +
                "   INNER JOIN jobs_register jr ON jr.job_id = j.id\n" +
                "   INNER JOIN status_job_register ps ON ps.id = jr.status_id\n" +
                "   AND ps.code = 'Đang phỏng vấn'),\n" +
                "     total_apply AS\n" +
                "  (SELECT count(*) total_apply\n" +
                "   FROM time_filtered_job j\n" +
                "   LEFT JOIN jobs_register jr ON jr.job_id = j.id),\n" +
                "     success_recruited_applicant AS\n" +
                "  (SELECT count(jr.job_id) success_recruited_applicant\n" +
                "   FROM time_filtered_job j\n" +
                "   INNER JOIN jobs_register jr ON jr.job_id = j.id\n" +
                "   INNER JOIN status_job_register ps ON ps.id = jr.status_id\n" +
                "   AND ps.code = 'Đã tuyển'),\n" +
                "     false_applicant AS\n" +
                "  (SELECT count(jr.job_id) false_applicant\n" +
                "   FROM time_filtered_job j\n" +
                "   INNER JOIN jobs_register jr ON jr.job_id = j.id\n" +
                "   INNER JOIN status_job_register ps ON ps.id = jr.status_id\n" +
                "   AND ps.code = 'Ứng viên bị từ chối')\n" +
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
    public int geNumberUserJe(){
        String query = "SELECT count(*) as countId\n" +
                "FROM USERS\n" +
                "JOIN PERMISSTION ON USERS.ID = PERMISSTION.USER_ID\n" +
                "JOIN ROLES ON ROLES.ID = PERMISSTION.ROLE_ID\n" +
                "WHERE ROLES.ID = 2";
        return getJdbcTemplate().queryForObject(query,Integer.class);
    }

    public LineChartDataResponse getDataLineChart(StatisticalVm statisticalVm){
        log.info("start ====> query find all category");
        String strS = statisticalVm.getDatestart();
        String strE = statisticalVm.getDateend();
        StringBuilder sql = new StringBuilder();
        String query = "SELECT extract(month from j.start_recruitment_date) as month, " +
                " SUM(j.Qty_Person) as numberRecruit, " +
                " COALESCE(SUM(CASE WHEN jr.status_id = 4 THEN 1 ELSE 0 END), 0) as successJob " +
                " from job j  left join jobs_register jr on j.id=jr.job_id " +
                " where j.start_recruitment_date BETWEEN to_date('"+strS+"', 'YYYY-MM-DD') AND to_date('"+strE+"', 'YYYY-MM-DD') " +
                " group by extract(month from j.start_recruitment_date) ORDER BY month ";
        List<Integer> month = getJdbcTemplate().query(query, new RowMapper<Integer>() {
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("month");
            }
        });
        List<Integer> numberRecruit = getJdbcTemplate().query(query, new RowMapper<Integer>() {
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("numberRecruit");
            }
        });
        List<Integer> numberSuccessJob = getJdbcTemplate().query(query, new RowMapper<Integer>() {
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("successJob");
            }
        });
        LineChartDataResponse result = new LineChartDataResponse();
        result.setMonth(month);
        result.setNumberRecruit(numberRecruit);
        result.setNumberSuccessJob(numberSuccessJob);
        return result;
    }

    public ColumnChartResponse getDataColumnChart(StatisticalVm statisticalVm){
        log.info("start ====> query find all category");
        String strS = statisticalVm.getDatestart();
        String strE = statisticalVm.getDateend();
        String query =" SELECT REGEXP_SUBSTR(j.skills, '[^ ]+', 1, level) AS languages, "+
                " sum(j.qty_person) as totalRecruit, count(jr.id) as numberApply " +
                "   FROM job j left join jobs_register jr on jr.job_id = j.id " +
                " where j.start_recruitment_date BETWEEN to_date('"+strS+"', 'YYYY-MM-DD') AND to_date('"+strE+"', 'YYYY-MM-DD') " +
                "   CONNECT BY REGEXP_SUBSTR(j.skills, '[^ ]+', 1, level) IS NOT NULL " +
                "  AND PRIOR j.rowid = j.rowid "+
                " AND PRIOR SYS_GUID() IS NOT NULL group by REGEXP_SUBSTR(j.skills, '[^ ]+', 1, level) ";
        List<String> languages = getJdbcTemplate().query(query, new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("languages");
            }
        });
        List<Integer> totalRecruit = getJdbcTemplate().query(query, new RowMapper<Integer>() {
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("totalRecruit");
            }
        });
        List<Integer> numberApply = getJdbcTemplate().query(query, new RowMapper<Integer>() {
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("numberApply");
            }
        });
        ColumnChartResponse result = new ColumnChartResponse();
        result.setLanguages(languages);
        result.setTotalRecruit(totalRecruit);
        result.setNumberApply(numberApply);
        return result;
    }
}

