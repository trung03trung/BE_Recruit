package com.itsol.recruit.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(value = {"page", "size", "sortName", "sortType", "sqlQuery", "parameters", "totalRow", "indexRow"}, allowSetters = true)
public class BaseObj {

    private Integer pageNo;
    private Integer pageSize;
    private String sortName;
    private String sortType;
    private String sqlQuery;
    private Map<String, Object> parameters;
    private Integer totalRow;
    private Integer indexRow;
}
