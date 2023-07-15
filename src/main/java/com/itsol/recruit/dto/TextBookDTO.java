package com.itsol.recruit.dto;

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

    Integer startNumber;

    Date applyDate;

    Date closeDate;

    String manageUser;
}
