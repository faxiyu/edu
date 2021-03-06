package com.fxy.staservice.service;

import com.fxy.staservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author fxy
 * @since 2020-11-09
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void regCount(String day);

    Map<String, Object> getChartData(String begin, String end, String type);
}
