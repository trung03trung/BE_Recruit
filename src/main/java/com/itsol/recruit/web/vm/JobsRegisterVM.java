package com.itsol.recruit.web.vm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobsRegisterVM {

    private List<JobsRegister> jobsRegisters;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;

    private String sortBy;

    private String sortDir;

    private String jobName;
    private String applyName;
    private String status;

    public JobsRegisterVM(List<JobsRegister> jobsRegisters, int pageNo, int pageSize, long totalElements, int totalPages, boolean last) {
        this.jobsRegisters = jobsRegisters;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }
}
