package com.fxy.eduorder.client;

import com.fxy.commonutils.orderVo.CourseVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name="service-edu")
public interface EduClient {
    @PostMapping("/eduservice/courseFornt/getCourseInfo/{courseId}")
    public CourseVoOrder getCourseInfo(@PathVariable("courseId") String courseId );
    @GetMapping("/eduservice/courseFornt/updateBuy/{courseId}")
    public void updateBuy(@PathVariable("courseId") String courseId);
}
