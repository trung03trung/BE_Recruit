package com.itsol.recruit.web.vm;

import com.itsol.recruit.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobVM {
    private List<Job> jobs;

    private int pageNo;

    private int pageSize;

    private long totalElements;

    private int totalPages;

    private boolean last;

    private String sortBy;

    private String sortDir;

    private String name;

    private String statusJob;
    private String jobPosition;

    public JobVM(List<Job> jobs, int pageNo, int pageSize, long totalElements, int totalPages, boolean last) {
        this.jobs = jobs;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }
}
