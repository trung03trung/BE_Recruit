package com.itsol.recruit.service.impl;

import com.itsol.recruit.service.JobService;
import com.itsol.recruit.service.JobsRegisterService;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WaitPvGauge {

    private final Gauge myGauge;
    private double gaugeValue;
    @Autowired
    private JobsRegisterService jobRegisterService;
    @Autowired
    public WaitPvGauge(MeterRegistry meterRegistry) {
        this.myGauge = Gauge.builder("dashboard_register_wait_pv", this::getGaugeValue)
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
        long register = jobRegisterService.getByDateAndStatus(new Date(), 2L);
        setGaugeValue(register);
    }
}
