package com.itsol.recruit.service;

import com.itsol.recruit.web.vm.JobsRegisterVM;

public interface JobsRegisterService {

    public JobsRegisterVM getAllJobsRegister(int pageNo, int pageSize, String sortBy, String sortDir);
}
