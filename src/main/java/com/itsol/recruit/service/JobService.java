package com.itsol.recruit.service;

import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.dto.PageExtDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.request.JobSearchRequest;
import com.itsol.recruit.dto.respone.JobSearchResponse;
import com.itsol.recruit.entity.Job;
import com.itsol.recruit.web.vm.JobFieldVM;
import com.itsol.recruit.web.vm.JobVM;
import com.itsol.recruit.web.vm.StatisticalVm;

import java.io.IOException;
import java.util.List;

public interface JobService {

    public JobVM getAllJob(int pageNo, int pageSize, String sortBy, String sortDir);

    public Job createNewJob(JobDTO jobDTO);

    public Job getJobById(Long id);

    public JobFieldVM getAllFieldSelect();

    public ResponseDTO changeStatus(Long id,String code);

    public List<Job> getAllJobPublic();

    public ResponseDTO rejectStatus(Long id,String code,String reason);

    public ResponseDTO deleteJobById(Long id);

    public JobVM searchJob(JobVM jobVM);

    public byte[] exportData() throws IOException;

    public byte[] exportDataDashboard(StatisticalVm statisticalVm) throws IOException;

}
