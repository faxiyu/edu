package com.fxy.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.eduservice.client.VodClient;
import com.fxy.eduservice.entity.EduCourse;
import com.fxy.eduservice.entity.EduCourseDescription;
import com.fxy.eduservice.entity.EduTeacher;
import com.fxy.eduservice.entity.frontVo.CourseFrontVo;
import com.fxy.eduservice.entity.frontVo.OneCourseVo;
import com.fxy.eduservice.entity.vo.CourseInfoVo;
import com.fxy.eduservice.entity.vo.CoursePublishVo;
import com.fxy.eduservice.entity.vo.CourseQuery;
import com.fxy.eduservice.mapper.EduCourseMapper;
import com.fxy.eduservice.service.EduChapterService;
import com.fxy.eduservice.service.EduCourseDescriptionService;
import com.fxy.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.eduservice.service.EduVideoService;
import com.fxy.servicebase.exceptionHandler.FxyException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    EduCourseDescriptionService courseDescriptionService;
    @Autowired
    EduVideoService videoService ;

    @Autowired
    EduChapterService chapterService;

    @Autowired
    private VodClient vodClient;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert<=0){
            throw new FxyException(20001,"添加课程失败");
        }
        EduCourseDescription courseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        courseDescription.setId(eduCourse.getId());
        boolean save = courseDescriptionService.save(courseDescription);


        return eduCourse.getId();
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {

        EduCourse eduCourse = baseMapper.selectById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        EduCourseDescription description = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());


        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,course);
        int i = baseMapper.updateById(course);
        if(i==0){
            throw new FxyException(20001,"修改课程信息失败");
        }
        EduCourseDescription description = new EduCourseDescription();
        description.setDescription(courseInfoVo.getDescription());
        boolean b = courseDescriptionService.updateById(description);

    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");

        if (courseQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();
        String status = courseQuery.getStatus();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(status)) {
            queryWrapper.eq("status", status);
        }


        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public void removeCourse(String courseId) {

        videoService.removeByCourseId(courseId);
        chapterService.removeByCourseId(courseId);
        courseDescriptionService.removeById(courseId);
        int i = baseMapper.deleteById(courseId);
        if(i==0){
            throw new FxyException(20001,"删除失败");

        }

    }

    @Cacheable(value = "teacher",key = "'selectIndexList'")
    @Override
    public List<EduCourse> getHotCourse() {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("view_count");
        courseQueryWrapper.last("limit 8");
        List<EduCourse> list1 = baseMapper.selectList(courseQueryWrapper);
        return list1;
    }

    @Override
    public Map<String, Object> getCourseFront(Page<EduCourse> page, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())) {
            queryWrapper.eq("subject_id", courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {
            queryWrapper.orderByDesc("price");
        }
        baseMapper.selectPage(page,queryWrapper);
        List<EduCourse> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;

    }

    @Override
    public OneCourseVo getCourseInfoFront(String courseId) {
        this.updatePageViewCount(courseId);
        return baseMapper.getCourseInfoFront(courseId);
    }
    public void updatePageViewCount(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }

}
