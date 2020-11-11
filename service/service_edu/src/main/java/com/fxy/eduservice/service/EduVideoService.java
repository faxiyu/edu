package com.fxy.eduservice.service;

import com.fxy.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);
}
