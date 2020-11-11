package com.fxy.eduservice.controller;


import com.fxy.commonutils.Rsponse;
import com.fxy.eduservice.entity.subject.OneSubject;
import com.fxy.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-10-30
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class EduSubjectController {
    @Autowired
    EduSubjectService subjectService;

    //添加课程分类
    @PostMapping("addSubject")
    public Rsponse addsubject(MultipartFile file){

        subjectService.saveSubject(file,subjectService);
        return Rsponse.ok();
    }

    @GetMapping("getAllSubject")
    public Rsponse getAllSubject(){
        List<OneSubject> list =  subjectService.getSubject();
        return Rsponse.ok().data("list",list);
    }


}

