package com.itsol.recruit.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageExtDTO<T>{
    private Integer totalElements;
    private Integer totalPages;
    private List<T> content;
    private int pageNo;

    private int pageSize;
    private Integer totalRow;
    private Integer indexRow;


    public PageExtDTO() {
    }

    /**
     * @param totalElements
     * @param totalPages
     * @param content
     */
    public PageExtDTO(Integer totalElements, Integer totalPages, List<T> content) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.content = content;
    }
}
