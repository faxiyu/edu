package com.fxy.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fxy.commonutils.Rsponse;
import com.fxy.staservice.client.UcenterClient;
import com.fxy.staservice.entity.StatisticsDaily;
import com.fxy.staservice.mapper.StatisticsDailyMapper;
import com.fxy.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author fxy
 * @since 2020-11-09
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
   @Autowired
   private UcenterClient ucenterClient;
    @Override
    public void regCount(String day) {

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        Rsponse r=ucenterClient.countRegister(day);
        Integer countRegister = (Integer) r.getData().get("countRegister");
        StatisticsDaily daily = new StatisticsDaily();
        daily.setCourseNum(countRegister);
        daily.setDateCalculated(day);
        daily.setLoginNum(RandomUtils.nextInt(100,200));
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));
        daily.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> getChartData(String begin, String end, String type) {

        QueryWrapper<StatisticsDaily> dayQueryWrapper = new QueryWrapper<>();
        dayQueryWrapper.select( "date_calculated",type);
        dayQueryWrapper.between("date_calculated", begin, end);

        List<StatisticsDaily> dayList = baseMapper.selectList(dayQueryWrapper);

        Map<String, Object> map = new HashMap<>();
        List<Integer> dataList = new ArrayList<Integer>();
        List<String> dateList = new ArrayList<String>();

        for (int i = 0; i < dayList.size(); i++) {
            StatisticsDaily daily = dayList.get(i);
            dateList.add(daily.getDateCalculated());
            switch (type) {
                case "register_num":
                    dataList.add(daily.getRegisterNum());
                    break;
                case "login_num":
                    dataList.add(daily.getLoginNum());
                    break;
                case "video_view_num":
                    dataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        return map;
    }
}
