package com.fxy.eduservice.mapper;

import com.fxy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fxy.eduservice.entity.frontVo.OneCourseVo;
import com.fxy.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(@Param("courseId") String courseId);

    public OneCourseVo getCourseInfoFront(String courseId);
}
