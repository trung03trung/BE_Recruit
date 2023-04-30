package com.itsol.recruit.service.impl;

import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.entity.*;
import com.itsol.recruit.repository.*;
import com.itsol.recruit.repository.repoimpl.JobRepositoryImpl;
import com.itsol.recruit.repository.repoimpl.UserRepositoryImpl;
import com.itsol.recruit.service.JobService;
import com.itsol.recruit.service.mapper.JobMapper;
import com.itsol.recruit.web.vm.JobFieldVM;
import com.itsol.recruit.web.vm.JobVM;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final JobMapper jobMapper;

    private final JobPositionRepository jobPositionRepository;

    private final RankRepository rankRepository;

    private final StatusJobRepository statusJobRepository;

    private final WorkingFormRepository workingFormRepository;

    private final AcademicLevelRepository academicLevelRepository;


    private final RoleRepository roleRepository;

    private final JobRepositoryImpl jobRepositoryimpl;

    private final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper, JobPositionRepository jobPositionRepository, RankRepository rankRepository, StatusJobRepository statusJobRepository, WorkingFormRepository workingFormRepository, AcademicLevelRepository academicLevelRepository, RoleRepository roleRepository, RoleRepository roleRepository1, JobRepositoryImpl jobRepositoryimpl) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.jobPositionRepository = jobPositionRepository;
        this.rankRepository = rankRepository;
        this.statusJobRepository = statusJobRepository;
        this.workingFormRepository = workingFormRepository;
        this.academicLevelRepository = academicLevelRepository;
        this.roleRepository = roleRepository1;
        this.jobRepositoryimpl = jobRepositoryimpl;
    }


    @Override
    public  JobVM getAllJob(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize,sort);
        Page<Job> jobs=jobRepository.findAll(pageable);
        JobVM jobVM=new JobVM(jobs.getContent(),jobs.getNumber(),jobs.getSize(),jobs.getTotalElements(),
                jobs.getTotalPages(),jobs.isLast());
        return jobVM;
    }

    @Override
    public Job createNewJob(JobDTO jobDTO) {
        Job job=jobMapper.toEntity(jobDTO);
        job.setCreatedDate(new Date());
        job.setUpdateDate(new Date());
        StatusJob statusJob=statusJobRepository.findStatusJobById((long)1);
        job.setStatusJob(statusJob);
        return jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findJobById(id);
    }

    @Override
    public JobFieldVM getAllFieldSelect() {
        List<AcademicLevel> academicLevels=academicLevelRepository.findAll();
        List<Rank> ranks=rankRepository.findAll();
        List<JobPosition> jobPositions=jobPositionRepository.findAll();
        List<WorkingForm> workingForms=workingFormRepository.findAll();
        List<Role> role=roleRepository.findByCode("ROLE_JE");
        List<User> users=jobRepositoryimpl.getAllByRole(role);
        return new JobFieldVM(academicLevels,jobPositions,ranks,workingForms,users);
    }

    @Override
    public ResponseDTO changeStatus(Long id, String code) {
        Job job=jobRepository.findJobById(id);
        StatusJob statusJob=statusJobRepository.findStatusJobByCode(code);
        job.setStatusJob(statusJob);
        jobRepository.save(job);
        return new ResponseDTO("Change status success");
    }

    @Override
    public List<Job> getAllJobPublic() {
        List<Job> jobList = (List<Job>) jobRepository.findAll();
        return jobList;
    }

    public ResponseDTO rejectStatus(Long id, String code, String reason) {
        Job job=jobRepository.findJobById(id);
        StatusJob statusJob=statusJobRepository.findStatusJobByCode(code);
        job.setStatusJob(statusJob);
        job.setReason(reason);
        jobRepository.save(job);
        return new ResponseDTO("Change status success");
    }

    public ResponseDTO deleteJobById(Long id){
        Job job=jobRepository.findJobById(id);
        jobRepository.delete(job);
        return new ResponseDTO("Delete job success");
    }

    @Override
    public JobVM searchJob(JobVM jobVM) {
        List<Job> jobs=jobMapper.toEntity(jobRepositoryimpl.seachUser(jobVM));
        jobVM.setJobs(jobs);
        return jobVM;
    }

    @Override
    public byte[] exportData() throws IOException {
        log.info("Request to exportData : {}");
        List<Job> lstDataExport = jobRepository.findAll(PageRequest.of(0,100)).getContent();
        return exportFile(lstDataExport, true, "FILE_EXPORT_JOB");
    }

    private byte[] exportFile(List<Job> jobs, boolean export, String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:templates/Template_Job.xlsx");
        Workbook workBook = null;
        InputStream inputStreamResource = null;
        try {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            inputStreamResource = resource.getInputStream();
            if (resource != null && inputStreamResource != null) {
                workBook = WorkbookFactory.create(inputStreamResource);
                Sheet sheet = workBook.getSheetAt(0);
                int i = 2;
                if (jobs != null && jobs.size() > 0) {
                    for (Job data : jobs) {
                        createCell(sheet, 0, i, String.valueOf(i-1));
                        createCell(sheet, 1, i, StringUtils.isNotBlank(data.getName()) ? data.getName() : "");
                        createCell(sheet, 2, i, StringUtils.isNotBlank(data.getJobPosition().getDescription()) ? data.getJobPosition().getDescription() : "");
                        createCell(sheet, 3, i, Integer.toString(data.getSalaryMin()));
                        createCell(sheet, 4, i, StringUtils.isNotBlank(data.getDueDate().toString()) ? data.getDueDate().toString() : "");
                        createCell(sheet, 5, i, StringUtils.isNotBlank(data.getNumberExperience()) ? data.getNumberExperience() : "");
                        createCell(sheet, 6, i, StringUtils.isNotBlank(data.getSkills()) ? data.getSkills() : "");
                        createCell(sheet, 7, i, StringUtils.isNotBlank(data.getUserContact().getName()) ? data.getUserContact().getName() : "");
                        createCell(sheet, 8, i, StringUtils.isNotBlank(data.getStatusJob().getCode()) ? data.getStatusJob().getCode() : "");
                        i++;
                    }
                    }
                }
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                workBook.write(out);
                return out.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != workBook || inputStreamResource != null)
                workBook.close();
        }
        return null;
    }

    public Cell createCell(Sheet sheet, int c, int r, String cellValue) {
        Row row = sheet.getRow(r);
        if (row == null) {
            row = sheet.createRow(r);
        }
        Cell cell = row.createCell(c);
        cell.setCellValue(cellValue);
        return cell;
    }

    public Cell createCell(Sheet sheet, int c, int r, String cellValue, CellStyle style) {
        Row row = sheet.getRow(r);
        if (row == null) {
            row = sheet.createRow(r);
        }
        Cell cell = row.createCell(c);
        cell.setCellValue(cellValue);
        cell.setCellStyle(style);
        return cell;
    }
}
