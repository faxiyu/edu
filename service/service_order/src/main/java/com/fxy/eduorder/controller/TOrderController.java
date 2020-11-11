package com.fxy.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fxy.commonutils.JwtUtils;
import com.fxy.commonutils.Rsponse;
import com.fxy.eduorder.entity.TOrder;
import com.fxy.eduorder.service.TOrderService;
import com.fxy.servicebase.exceptionHandler.FxyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-11-09
 */
@RestController
@RequestMapping("/eduorder/order")

public class TOrderController {

    @Autowired
    private TOrderService orderService;

    @GetMapping("createOrder/{courseId}")
    public Rsponse createOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberIdByJwtToken)){
            throw new FxyException(20001,"请先登录");
        }
        String orderId = orderService.saveOrder(courseId,memberIdByJwtToken );
        return Rsponse.ok().data("orderId",orderId);
    }
    @GetMapping("getOrderInfo/{orderId}")
    public  Rsponse getOrderInfo(@PathVariable String orderId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder byId = orderService.getOne(wrapper);
        return Rsponse.ok().data("item",byId);
    }

    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId ){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = orderService.count(wrapper);
        if (count>0){
            return true;
        }else {
            return false;
        }
    }

}

