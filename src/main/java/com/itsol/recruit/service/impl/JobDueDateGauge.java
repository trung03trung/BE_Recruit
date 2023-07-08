package com.itsol.recruit.service.impl;

import com.itsol.recruit.service.JobService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobDueDateGauge {
    private final Gauge myGauge;
    private double gaugeValue;
    @Autowired
    private JobService jobService;
    @Autowired
    public JobDueDateGauge(MeterRegistry meterRegistry) {
        this.myGauge = Gauge.builder("dashboard_job_due_date", this::getGaugeValue)
                .register(meterRegistry);
    }

    public synchronized void setGaugeValue(double value) {
        this.gaugeValue = value;
    }

    public synchronized double getGaugeValue() {
        return gaugeValue;
    }

    @Scheduled(fixedDelay = 5000) // Cập nhật giá trị mỗi giây
    public void updateGaugeValue() {
        long register = jobService.getTotalJobDueDate();
        setGaugeValue(register);
    }
}
