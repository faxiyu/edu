package com.fxy.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.eduservice.entity.frontVo.CourseFrontVo;
import com.fxy.eduservice.entity.frontVo.OneCourseVo;
import com.fxy.eduservice.entity.vo.CourseInfoVo;
import com.fxy.eduservice.entity.vo.CoursePublishVo;
import com.fxy.eduservice.entity.vo.CourseQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo publishCourseInfo(String id);

    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    void removeCourse(String courseId);

    List<EduCourse> getHotCourse();

    Map<String, Object> getCourseFront(Page<EduCourse> page, CourseFrontVo courseFrontVo);

    OneCourseVo getCourseInfoFront(String courseId);
}
