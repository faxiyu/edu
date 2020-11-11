package com.fxy.staservice.schedlu;


import com.fxy.staservice.service.StatisticsDailyService;
import com.fxy.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService dailyService;
    @Scheduled(cron = "0 0 1 * * ?")
    public void task(){
        dailyService.regCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
