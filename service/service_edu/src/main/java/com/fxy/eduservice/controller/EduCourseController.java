package com.fxy.eduservice.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.commonutils.Rsponse;
import com.fxy.eduservice.entity.EduCourse;
import com.fxy.eduservice.entity.vo.CourseInfoVo;
import com.fxy.eduservice.entity.vo.CoursePublishVo;
import com.fxy.eduservice.entity.vo.CourseQuery;
import com.fxy.eduservice.service.EduCourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-10-31
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {

    @Autowired
    EduCourseService courseService;

    @ApiOperation(value = "分页课程列表")
    @PostMapping("{current}/{size}")
    public Rsponse pageQuery(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,
            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    @RequestBody
                    CourseQuery courseQuery){

        Page<EduCourse> pageParam = new Page<>(current, size);

        courseService.pageQuery(pageParam, courseQuery);
        List<EduCourse> records = pageParam.getRecords();

        long total = pageParam.getTotal();

        return  Rsponse.ok().data("total", total).data("rows", records);
    }


    @PostMapping("addCourseInfo")
    public Rsponse addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id=courseService.saveCourseInfo(courseInfoVo);

        return Rsponse.ok().data("courseId",id);
    }

    @GetMapping("getCourseInfo/{courseId}")
    public Rsponse getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return Rsponse.ok().data("courseInfoVo",courseInfoVo);
    }
    @PostMapping("updateCourseInfo")
    public  Rsponse updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return Rsponse.ok();
    }
    @GetMapping("getPublishCourseInfo/{id}")
    public  Rsponse getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return Rsponse.ok().data("coursePublishVo",coursePublishVo);
    }
    @PostMapping("publishCourse/{id}")
    public  Rsponse publishCourse(@PathVariable String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return Rsponse.ok();
    }
    @GetMapping("getCourseList")
    public Rsponse getCourseList(){
        List<EduCourse> list = courseService.list(null);
        return Rsponse.ok().data("list",list);
    }

    @DeleteMapping("/{courseId}")
    public Rsponse deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return Rsponse.ok();
    }

}

