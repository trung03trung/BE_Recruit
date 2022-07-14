package com.itsol.recruit.web.vm;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
public class StatisticalVm {
    @Nullable
    Number month;
    @Nullable
    Date dateend;
    @Nullable
    Date datestart;
}
