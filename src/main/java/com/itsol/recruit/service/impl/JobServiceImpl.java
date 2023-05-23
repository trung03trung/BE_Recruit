package com.itsol.recruit.service.impl;

import com.itsol.recruit.core.Constants;
import com.itsol.recruit.dto.JobDTO;
import com.itsol.recruit.dto.ResponseDTO;
import com.itsol.recruit.dto.StatisticalDTO;
import com.itsol.recruit.dto.respone.ColumnChartResponse;
import com.itsol.recruit.dto.respone.JobDetailResponse;
import com.itsol.recruit.dto.respone.LineChartDataResponse;
import com.itsol.recruit.entity.*;
import com.itsol.recruit.repository.*;
import com.itsol.recruit.repository.repoimpl.JobRepositoryImpl;
import com.itsol.recruit.repository.repoimpl.UserRepositoryImpl;
import com.itsol.recruit.service.JobService;
import com.itsol.recruit.service.mapper.JobMapper;
import com.itsol.recruit.web.vm.JobFieldVM;
import com.itsol.recruit.web.vm.JobVM;
import com.itsol.recruit.web.vm.StatisticalVm;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

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

    private final UserRepositoryImpl userRepositoryImpl;

    private final CompanyRepository companyRepository;
    private final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private ResourceLoader resourceLoader;

    public JobServiceImpl(JobRepository jobRepository, JobMapper jobMapper, JobPositionRepository jobPositionRepository, RankRepository rankRepository, StatusJobRepository statusJobRepository, WorkingFormRepository workingFormRepository, AcademicLevelRepository academicLevelRepository, RoleRepository roleRepository, RoleRepository roleRepository1, JobRepositoryImpl jobRepositoryimpl, UserRepositoryImpl userRepositoryImpl, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
        this.jobPositionRepository = jobPositionRepository;
        this.rankRepository = rankRepository;
        this.statusJobRepository = statusJobRepository;
        this.workingFormRepository = workingFormRepository;
        this.academicLevelRepository = academicLevelRepository;
        this.roleRepository = roleRepository1;
        this.jobRepositoryimpl = jobRepositoryimpl;
        this.userRepositoryImpl = userRepositoryImpl;
        this.companyRepository = companyRepository;
    }


    @Override
    public JobVM getAllJob(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Job> jobs = jobRepository.findAll(pageable);
        JobVM jobVM = new JobVM(jobs.getContent(), jobs.getNumber(), jobs.getSize(), jobs.getTotalElements(),
                jobs.getTotalPages(), jobs.isLast());
        return jobVM;
    }

    @Override
    public Job createNewJob(JobDTO jobDTO) {
        Job job = jobMapper.toEntity(jobDTO);
        job.setCreatedDate(new Date());
        job.setUpdateDate(new Date());
        StatusJob statusJob = statusJobRepository.findStatusJobById((long) 1);
        job.setStatusJob(statusJob);
        return jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findJobById(id);
    }

    @Override
    public JobDetailResponse getJobDetailById(Long id) {
        Job job = jobRepository.findJobById(id);
        JobDetailResponse response = new JobDetailResponse();
        BeanUtils.copyProperties(job,response);
        String[] descriptions = job.getDescription().split("\\s*-\\s*");
        String[] interrests = job.getInterrest().split("-");
        String[] requirements = job.getJobRequirement().split("\\s*-\\s*");
        response.setDescription(new ArrayList<>(Arrays.asList(descriptions)));
        response.setInterrest(new ArrayList<>(Arrays.asList(interrests)));
        response.setJobRequirement(new ArrayList<>(Arrays.asList(requirements)));
        return response;
    }

    @Override
    public JobFieldVM getAllFieldSelect() {
        List<AcademicLevel> academicLevels = academicLevelRepository.findAll();
        List<Rank> ranks = rankRepository.findAll();
        List<JobPosition> jobPositions = jobPositionRepository.findAll();
        List<WorkingForm> workingForms = workingFormRepository.findAll();
        List<Role> role = roleRepository.findByCode("ROLE_JE");
        List<User> users = jobRepositoryimpl.getAllByRole(role);
        List<Company> companies = companyRepository.findAll();
        return new JobFieldVM(academicLevels, jobPositions, ranks, workingForms, users,companies);
    }

    @Override
    public ResponseDTO changeStatus(Long id, String code) {
        Job job = jobRepository.findJobById(id);
        StatusJob statusJob = statusJobRepository.findStatusJobByCode(code);
        job.setStatusJob(statusJob);
        jobRepository.save(job);
        return new ResponseDTO("Change status success");
    }

    @Override
    public List<Job> getJobPublic(String type) {
        List<Job> jobList =new ArrayList<>();
        if(type.equals(Constants.TypeJob.FEATURED)){
            Page<Job> page = jobRepository.findAll(
                    PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "views")));
            return page.getContent();
        }
        Page<Job> page = jobRepository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdDate")));
        return page.getContent();
    }

    public ResponseDTO rejectStatus(Long id, String code, String reason) {
        Job job = jobRepository.findJobById(id);
        StatusJob statusJob = statusJobRepository.findStatusJobByCode(code);
        job.setStatusJob(statusJob);
        job.setReason(reason);
        jobRepository.save(job);
        return new ResponseDTO("Change status success");
    }

    public ResponseDTO deleteJobById(Long id) {
        Job job = jobRepository.findJobById(id);
        jobRepository.delete(job);
        return new ResponseDTO("Delete job success");
    }

    @Override
    public JobVM searchJob(JobVM jobVM) {
        List<Job> jobs = jobMapper.toEntity(jobRepositoryimpl.seachUser(jobVM));
        jobVM.setJobs(jobs);
        return jobVM;
    }

    @Override
    public byte[] exportData() throws IOException {
        log.info("Request to exportData : {}");
        List<Job> lstDataExport = jobRepository.findAll(PageRequest.of(0, 100)).getContent();
        return exportFile(lstDataExport, true, "FILE_EXPORT_JOB");
    }

    @Override
    public byte[] exportDataDashboard(StatisticalVm statisticalVm) throws IOException {
        log.info("Request to exportDataDashboard : {}",statisticalVm);
        Resource resource = new ClassPathResource("templates/Template_Dashboard.xlsx");
        Workbook workBook = WorkbookFactory.create(resource.getInputStream());
        try {
            List<StatisticalDTO> statisticalDTOS = userRepositoryImpl.StatisticalData(statisticalVm);
            Sheet sheet = workBook.getSheetAt(0);
            CellStyle cellSt0 = sheet.getRow(2).getCell(1).getCellStyle();
            createCell(sheet,1,2,"Từ "+statisticalVm.getDatestart()+" đến "+statisticalVm.getDateend(),cellSt0);
            CellStyle cellSt1 = sheet.getRow(6).getCell(1).getCellStyle();
            createCell(sheet, 1, 6, Integer.toString(statisticalDTOS.get(0).getAll_job()),cellSt1);
            createCell(sheet, 2, 6,Integer.toString(statisticalDTOS.get(0).getTotal_apply()),cellSt1);
            createCell(sheet, 3, 6, Integer.toString(statisticalDTOS.get(0).getInterviewing()),cellSt1);
            createCell(sheet, 4, 6, Integer.toString(statisticalDTOS.get(0).getSuccess_recruited_applicant()),cellSt1);
            createCell(sheet, 5, 6, Integer.toString(statisticalDTOS.get(0).getFalse_applicant()),cellSt1);
            LineChartDataResponse dataLineChart = userRepositoryImpl.getDataLineChart(statisticalVm);
            CellStyle cellSt2 = sheet.getRow(13).getCell(1).getCellStyle();
            int i=2,j=2,k=2;
            for(Integer month: dataLineChart.getMonth()) {
                createCell(sheet, i, 13, "Tháng " + Integer.toString(month), cellSt2);
                i++;
            }
            CellStyle cellSt3 = sheet.getRow(15).getCell(2).getCellStyle();
            for(Integer data: dataLineChart.getNumberRecruit()) {
                createCell(sheet, j, 14, data, cellSt3);
                j++;
            }
            for(Integer data: dataLineChart.getNumberSuccessJob()) {
                createCell(sheet, k, 15, data, cellSt3);
                k++;
            }
            i=19;j=19;k=19;
            CellStyle cellSt4 = sheet.getRow(19).getCell(1).getCellStyle();
            ColumnChartResponse columnChartResponse = userRepositoryImpl.getDataColumnChart(statisticalVm);
            for(String data:columnChartResponse.getLanguages()){
                createCell(sheet,1,i,data,cellSt4);
                i++;
            }
            CellStyle cellSt5 = sheet.getRow(19).getCell(4).getCellStyle();
            for(Integer data:columnChartResponse.getNumberApply()){
                createCell(sheet,4,j,data,cellSt5);
                j++;
            }
            for(Integer data:columnChartResponse.getTotalRecruit()){
                createCell(sheet,5,k,data,cellSt5);
                k++;
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workBook.write(out);
            out.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != workBook)
                workBook.close();
        }
        return null;
    }

    private byte[] exportFile(List<Job> jobs, boolean export, String fileName) throws IOException {
        Resource resource = new ClassPathResource("templates/Template_Jobs.xlsx");
        Workbook workBook = WorkbookFactory.create(resource.getInputStream());
        try {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);


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

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workBook.write(out);
            out.close();
            return out.toByteArray();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != workBook)
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

    public Cell createCell(Sheet sheet, int c, int r, Integer cellValue, CellStyle style) {
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
