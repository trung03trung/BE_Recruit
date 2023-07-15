package com.itsol.recruit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
@AllArgsConstructor
public class TextBookDTO {
    String id;

    String name;

    Integer startNumber;

    Date applyDate;

    Date closeDate;

    String manageUser;
}
