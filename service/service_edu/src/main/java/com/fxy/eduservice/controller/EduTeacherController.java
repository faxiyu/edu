package com.fxy.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.commonutils.Rsponse;
import com.fxy.eduservice.entity.vo.TeacherQuery;
import com.fxy.eduservice.service.EduTeacherService;
import com.fxy.eduservice.entity.EduTeacher;
import com.fxy.servicebase.exceptionHandler.FxyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-10-26
 */

@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")

public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Rsponse findAllTeacher(){
        List<EduTeacher> list = teacherService.list(null);
//        try {
//            int i=10/0;
//        }catch (Exception e){
//            throw new FxyException(20001,"执行自定义异常处理");
//        }
        return Rsponse.ok().data("item",list);
    }
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public Rsponse remove(
            @ApiParam(value = "讲师ID")
            @PathVariable String id){
        boolean b = teacherService.removeById(id);
         return b? Rsponse.ok():Rsponse.error();
    }
    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeacher/{current}/{size}")
    public Rsponse pageList(
            @ApiParam("当前页")
            @PathVariable("current") long current,
            @ApiParam("每页大小")
            @PathVariable("size") long size){

        Page<EduTeacher> pageTeacher = new Page<EduTeacher>(current,size);
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return Rsponse.ok().data("total",total).data("records",records);
    }
    @ApiOperation("条件查询带分页")
    @PostMapping("pgeTeacherCondition/{current}/{size}")
    public  Rsponse pgeTeacherCondition(
            @ApiParam("当前页")
            @PathVariable("current") long current,
            @ApiParam("每页大小")
            @PathVariable("size") long size,@RequestBody(required = false)  TeacherQuery teacherQuery){
        Page<EduTeacher> teacherPage = new Page<>(current,size);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name))
            wrapper.like("name",name);
        if (!StringUtils.isEmpty(level))
            wrapper.eq("level",level);
        if (!StringUtils.isEmpty(begin))
            wrapper.ge("gmt_create",begin);
        if (!StringUtils.isEmpty(end))
            wrapper.le("gmt_modified", end);
        wrapper.orderByDesc("gmt_create");
        teacherService.page(teacherPage,wrapper);
        long total = teacherPage.getTotal();
        List<EduTeacher> records = teacherPage.getRecords();
        return Rsponse.ok().data("total",total).data("records",records);

    }
    @ApiOperation("添加讲师接口")
    @PostMapping("addTeacher")
    public Rsponse addTeacher(@RequestBody EduTeacher teacher){
        boolean save = teacherService.save(teacher);
        if (save) return Rsponse.ok();
        else return  Rsponse.error();
    }
    @ApiOperation("根据讲师id查询")
    @GetMapping("getTeacher/{id}")
    public  Rsponse getTeacher(@PathVariable String id){
        EduTeacher byId = teacherService.getById(id);
        return Rsponse.ok().data("teacher",byId);
    }

    @ApiOperation("讲师修改功能")
    @PostMapping("updateTeacher")
    public Rsponse updateTeacher(@RequestBody EduTeacher teacher){
        boolean b = teacherService.updateById(teacher);
        return b? Rsponse.ok():Rsponse.error();
    }


}

