package com.itsol.recruit.dto;

import lombok.Data;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.util.Date;
import java.util.List;

@Data
@SqlResultSetMapping(
        name = "UserInfoMapping",
        classes = @ConstructorResult(
                targetClass = PageExtDTO.class,
                columns = {
                        @ColumnResult(name = "totalElements", type = Integer.class),
                        @ColumnResult(name = "id", type = String.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "startNumber", type = Integer.class),
                        @ColumnResult(name = "applyDate", type = Date.class),
                        @ColumnResult(name = "closeDate", type = Date.class),
                        @ColumnResult(name = "manageUser", type = String.class)
                }
        )
)
public class PageExtDTO{
    private Integer totalElements;
    private Integer totalPages;
    private List<TextBookDTO> content;
    private int pageNo;

    private int pageSize;


    public PageExtDTO() {
    }

    public PageExtDTO(Integer totalElements,List<TextBookDTO> content) {
        this.totalElements = totalElements;
        this.content = content;
    }
}
