package com.fxy.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.commonutils.Rsponse;
import com.fxy.eduservice.entity.EduCourse;
import com.fxy.eduservice.entity.EduTeacher;
import com.fxy.eduservice.service.EduCourseService;
import com.fxy.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherFornt")
@CrossOrigin
public class teacherForntController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeacher/{current}/{size}")
    public Rsponse pageList(@PathVariable("current") long current,@PathVariable("size") long size){

        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(current,size);
        Map<String,Object> map = teacherService.pageTeacherFront(pageTeacher);
        return Rsponse.ok().data(map);
    }

    @GetMapping("getTeacherFrontInfo/{id}")
    public Rsponse get(@PathVariable String id){
        EduTeacher teacher = teacherService.getById(id);

        QueryWrapper<EduCourse > wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> list = courseService.list(wrapper);

        return Rsponse.ok().data("teacher",teacher).data("courseList",list);
    }
}
