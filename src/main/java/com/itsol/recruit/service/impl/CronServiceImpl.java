package com.itsol.recruit.service.impl;

import com.itsol.recruit.service.JobService;
import com.itsol.recruit.service.JobsRegisterService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CronServiceImpl {

    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    private JobsRegisterService jobRegisterService;

    @Autowired
    private JobService jobService;

//    @Scheduled(cron = "0/20 * * * * ?")
//    public void getRegisterWaitPv(){
//        // status = 2 Doi phong van
//        long register = jobRegisterService.getByDateAndStatus(new Date(), 2L);
//        Gauge.builder("dashboard_register_wait_pv", () -> register)
//                .description("Register wait pv")
//                .register(meterRegistry);
//    }

//    @Scheduled(cron = "0/20 * * * * ?")
//    public void getJobDueDate(){
//        // Lay so job sap hen han trong vong 1 tuan
//        long register = jobService.getTotalJobDueDate();
//        Gauge.builder("dashboard_job_due_date", () -> register)
//                .description("Job due date")
//                .register(meterRegistry);
//    }
}
