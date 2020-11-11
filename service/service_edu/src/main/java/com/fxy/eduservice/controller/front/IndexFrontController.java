package com.fxy.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fxy.commonutils.Rsponse;
import com.fxy.eduservice.entity.EduCourse;
import com.fxy.eduservice.entity.EduTeacher;
import com.fxy.eduservice.service.EduCourseService;
import com.fxy.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexFront")
@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    @GetMapping("index")
    public Rsponse index(){
        List<EduCourse> list1 =courseService.getHotCourse();
        List<EduTeacher> list =teacherService.getHotTeacher();

        return Rsponse.ok().data("courseList",list1).data("teacherList",list);
    }
}
