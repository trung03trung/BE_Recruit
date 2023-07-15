package com.itsol.recruit.dto;

import lombok.Data;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class PageExtDTO<T>{
    private Integer totalElements;
    private Integer totalPages;
    private List<T> content;
    private int pageNo;

    private int pageSize;
    private String sqlQuery;
    private Map<String, Object> parameters;
    private Integer totalRow;
    private Integer indexRow;



    public PageExtDTO() {
    }

    public PageExtDTO(Integer totalElements,List<T> content) {
        this.totalElements = totalElements;
        this.content = content;
    }
}
