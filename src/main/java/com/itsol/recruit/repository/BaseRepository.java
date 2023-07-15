package com.itsol.recruit.repository;

import com.itsol.recruit.dto.PageExtDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class BaseRepository {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplateNormal() {
        return namedParameterJdbcTemplate;
    }

    public PageExtDTO getListDataTableBySqlQuery(String sqlQuery,
                                                 Map<String, Object> parameters,
                                                 int page, int pageSize,
                                                 Class<?> mappedClass,
                                                 String sortName, String sortType) {
        Date startTime = new Date();
        PageExtDTO dataReturn = new PageExtDTO();
        StringBuilder sqlQueryResult = new StringBuilder(" SELECT a.*,ROW_NUMBER() OVER() as indexrow, COUNT(*) OVER() AS totalRow FROM ( ");
        if (StringUtils.isNotBlank(sortName)) {
            Field[] fields = FieldUtils.getAllFields(mappedClass);
            Map<String, String> mapField = new HashMap<>();
            for (Field field : fields) {
                mapField.put(field.getName(), field.getName());
            }
            sqlQueryResult.append(" SELECT b.* FROM (  ");
            sqlQueryResult.append(sqlQuery);
            sqlQueryResult.append(" ) b  ");
            if ("asc".equalsIgnoreCase(sortType)) {
                sqlQueryResult.append(" ORDER BY b." + mapField.get(sortName) + "  asc");
            } else if ("desc".equalsIgnoreCase(sortType)) {
                sqlQueryResult.append(" ORDER BY b." + mapField.get(sortName) + "  desc");
            }else {
                sqlQueryResult.append(" ORDER BY b." + mapField.get(sortName));
            }
        } else {
            sqlQueryResult.append(sqlQuery);
        }
        sqlQueryResult.append(") a  LIMIT :p_page_length OFFSET :p_page_number");
        int offset= (page-1)*pageSize;
        parameters.put("p_page_number", offset);
        parameters.put("p_page_length", pageSize);
        List<?> list = getNamedParameterJdbcTemplateNormal()
                .query(sqlQueryResult.toString(), parameters, BeanPropertyRowMapper.newInstance(mappedClass));
        int count = 0;
        if (list.isEmpty()) {
            dataReturn.setTotalElements(count);
        } else {
            try {
                Object obj = list.get(0);
                Field field = obj.getClass().getSuperclass().getDeclaredField("totalRow");
                field.setAccessible(true);
                Object objCount = field.get(obj);
                if (objCount != null && objCount.toString().trim() != "") {
                    count = Integer.parseInt(objCount.toString());
                    dataReturn.setTotalElements(count);
                }
            } catch (Exception e) {
              e.printStackTrace();
            }
        }
        if (pageSize > 0) {
            if (count % pageSize == 0) {
                dataReturn.setTotalPages(count / pageSize);
            } else {
                dataReturn.setTotalPages((count / pageSize) + 1);
            }
        }
        dataReturn.setContent(list);
        return dataReturn;
    }
}
