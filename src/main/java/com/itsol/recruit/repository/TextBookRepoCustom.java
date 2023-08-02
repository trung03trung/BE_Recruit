package com.itsol.recruit.repository;

import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.dto.TextBookDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TextBookRepoCustom extends BaseRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PageExtDTO search(String keyword,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Map<String, Object> parameters = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT t.id id, ");
        sql.append("  t.code code, ");
        sql.append(" t.name name, ");
        sql.append(" t.start_number startNumber , ");
        sql.append(" t.apply_date applyDate, ");
        sql.append(" t.close_date closeDate, ");
        sql.append(" t.manage_user manageUser ");
        sql.append(" FROM text_book t ");
        sql.append("  WHERE 1 = 1  ");

        if (StringUtils.isNotBlank(keyword)) {
                sql.append("   AND  LOWER(t.name) like '%' :keyword  '%' ");
            sql.append("   OR  LOWER(t.manage_user) like '%' :keyword  '%' ");
                parameters.put("keyword",keyword);
        }

        return getListDataTableBySqlQuery(sql.toString(), parameters, pageNumber
                , pageSize, TextBookDTO.class,sortBy,
                sortDir);
    }
}
