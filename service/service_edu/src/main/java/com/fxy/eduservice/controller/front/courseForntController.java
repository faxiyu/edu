package com.fxy.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.fxy.commonutils.JwtUtils;
import com.fxy.commonutils.Rsponse;
import com.fxy.commonutils.orderVo.CourseVoOrder;
import com.fxy.eduservice.client.OrdersClient;
import com.fxy.eduservice.entity.EduCourse;
import com.fxy.eduservice.entity.EduTeacher;
import com.fxy.eduservice.entity.chapter.ChapterVo;
import com.fxy.eduservice.entity.frontVo.CourseFrontVo;
import com.fxy.eduservice.entity.frontVo.OneCourseVo;
import com.fxy.eduservice.service.EduChapterService;
import com.fxy.eduservice.service.EduCourseService;
import com.fxy.eduservice.service.EduTeacherService;
import com.fxy.eduservice.service.EduVideoService;
import com.fxy.servicebase.exceptionHandler.FxyException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/courseFornt")
@CrossOrigin
public class courseForntController {

    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrdersClient ordersClient;

    @ApiOperation(value = "分页查询")
    @PostMapping("pageCourse/{current}/{size}")
    public Rsponse pageList(@PathVariable("current") long current, @PathVariable("size") long size, @RequestBody(required = false) CourseFrontVo courseFrontVo){

        Page<EduCourse> page = new Page<>(current,size);
        Map<String,Object> map = courseService.getCourseFront(page,courseFrontVo);
        return Rsponse.ok().data(map);
    }

    @GetMapping("getFrontCourseInfo/{courseId}")
    public Rsponse getFrontCourseInfo(@PathVariable String courseId , HttpServletRequest request){
        OneCourseVo oneCourseVo = courseService.getCourseInfoFront(courseId);
        List<ChapterVo> list = chapterService.getChapterVideoById(courseId);
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        boolean buyCourse ;
        if (!StringUtils.isEmpty(memberIdByJwtToken)){
            buyCourse = ordersClient.isBuyCourse(courseId, memberIdByJwtToken);
        }else {
            buyCourse=false;
        }
        return Rsponse.ok().data("oneCourseVo",oneCourseVo).data("list",list).data("isBuy",buyCourse);
    }

    @PostMapping("getCourseInfo/{courseId}")
    public CourseVoOrder getCourseInfo(@PathVariable String courseId ){
        OneCourseVo oneCourseVo = courseService.getCourseInfoFront(courseId);
        CourseVoOrder order = new CourseVoOrder();
        BeanUtils.copyProperties(oneCourseVo,order);
        return order;
    }
    @GetMapping("updateBuy/{courseId}")
    public void updateBuy(@PathVariable String courseId){
        EduCourse byId = courseService.getById(courseId);
        Long buyCount = byId.getBuyCount();
        byId.setBuyCount(buyCount+1);
        boolean b = courseService.updateById(byId);
        if (!b){
            throw new FxyException(20001,"购买量更新失败");
        }

    }
}
