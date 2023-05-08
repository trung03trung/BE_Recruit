package com.itsol.recruit.dto.request;

import com.itsol.recruit.dto.BaseObj;
import lombok.Data;

@Data
public class JobSearchRequest extends BaseObj {
    private String name;
    private String jobPosition;

    private String statusJob;
}
