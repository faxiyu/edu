package com.fxy.staservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.fxy.commonutils.Rsponse;
import com.fxy.staservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-11-09
 */
@RestController
@RequestMapping("/staservice/statistics-daily")

public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService dailyService;

    @PostMapping("registerCount/{day}")
    public Rsponse registerCount(@PathVariable String day){
        dailyService.regCount(day);
        return Rsponse.ok();
    }
    @GetMapping("showChart/{begin}/{end}/{type}")
    public Rsponse showChart(@PathVariable String begin, @PathVariable String end, @PathVariable String type){
        Map<String, Object> map = dailyService.getChartData(begin, end, type);
        return Rsponse.ok().data(map);
    }
}

