package com.itsol.recruit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextBookDTO extends BaseObj {
    String id;

    String name;

    String code;
    Integer startNumber;

    @JsonFormat(pattern="yyyy-MM-dd")
    Date applyDate;

    @JsonFormat(pattern="yyyy-MM-dd")
    Date closeDate;

    String manageUser;
}
