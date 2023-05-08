package com.itsol.recruit.repository;

import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.service.impl.JobServiceImpl;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Repository
public class BaseRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    JdbcTemplate jdbcTemplate;

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(BaseRepository.class);

    public Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplateNormal() {
        return namedParameterJdbcTemplate;
    }
    public PageExtDTO getListDataTableBySqlQuery(String sqlQuery,
                                                 Map<String, Object> parameters,
                                                 int page, int pageSize,
                                                 Class<?> mappedClass,
                                                 String sortName, String sortType) {
        log.info("--- Start request to search data {} ---");
        Date startTime = new Date();
        PageExtDTO dataReturn = new PageExtDTO();
        StringBuilder sqlQueryResult = new StringBuilder(" SELECT a.*,ROW_NUMBER() OVER(ORDER by a.id) as indexrow, COUNT(*) OVER() AS totalRow FROM ( ");
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
        sqlQueryResult.append(") a OFFSET ( :p_page_number - 1 ) * :p_page_length ROWS FETCH NEXT :p_page_length ROWS ONLY");
        parameters.put("p_page_number", page);
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
                log.debug(e.getMessage(), e);
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
        log.info("------End search : time " + (new Date().getTime() - startTime.getTime()) + " miliseconds");
        return dataReturn;
    }
}
