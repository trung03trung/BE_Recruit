package com.itsol.recruit.service;

import com.itsol.recruit.dto.JobsRegisterDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.JobsRegister;
import com.itsol.recruit.entity.Profile;
import com.itsol.recruit.web.vm.FilePdfVM;
import com.itsol.recruit.web.vm.JobRegisterPublicVM;
import com.itsol.recruit.web.vm.JobsRegisterVM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface JobsRegisterService {

    public JobsRegisterVM getAllJobsRegister(int pageNo, int pageSize, String sortBy, String sortDir);

    public JobsRegister getById(Long id);

    public Profile getProfileByJobRegister(Long id);

    public ResponseDTO changeStatus(Long id,String code);

    public ResponseDTO rejectStatus(Long id,String code,String reason);

    public ResponseDTO scheduleInterview(JobsRegisterDTO jobsRegisterDTO);

    public JobsRegisterVM searchJobRegister(JobsRegisterVM jobsRegisterVM);

    public ResponseEntity<ResponseDTO> addJobRegister(JobRegisterPublicVM jobRegisterPublicVM);
}
