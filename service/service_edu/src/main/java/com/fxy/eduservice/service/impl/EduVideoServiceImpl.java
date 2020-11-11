package com.fxy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fxy.eduservice.client.VodClient;
import com.fxy.eduservice.entity.EduVideo;
import com.fxy.eduservice.mapper.EduVideoMapper;
import com.fxy.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("course_id",courseId);
        wrapper1.select("video_source_id");
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper1);
        List<String> videoId = new ArrayList<>();
        for (int i = 0; i < eduVideos.size(); i++) {
            EduVideo video = eduVideos.get(i);
            String videoSourceId = video.getVideoSourceId();
            if (!StringUtils.isEmpty(videoSourceId)) {
                videoId.add(videoSourceId);
            }
        }
        if (videoId.size()>0) {
            vodClient.deleteBatch(videoId);
        }


        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
