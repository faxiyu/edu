package com.fxy.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fxy.commonutils.Rsponse;
import com.fxy.eduorder.client.EduClient;
import com.fxy.eduorder.entity.TOrder;
import com.fxy.eduorder.service.TOrderService;
import com.fxy.eduorder.service.TPayLogService;
import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-11-09
 */
@RestController
@RequestMapping("/eduorder/log")

public class TPayLogController {
    @Autowired
    private TPayLogService payService;
    @Autowired
    private TOrderService orderService;
    @Autowired
    private EduClient eduClient;
    /**
     * 生成二维码
     *
     * @return
     */
    @GetMapping("/createNative/{orderNo}")
    public Rsponse createNative(@PathVariable String orderNo) {
        Map map = payService.createNative(orderNo);
        return Rsponse.ok().data(map);
    }
    @GetMapping("/queryPayStatus/{orderNo}")
    public Rsponse queryPayStatus(@PathVariable String orderNo) {
        //调用查询接口
        Map<String, String> map = payService.queryPayStatus(orderNo);
        if (map == null) {//出错
            return Rsponse.error().message("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")) {//如果成功
            //更改订单状态
            QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            TOrder one = orderService.getOne(wrapper);

            eduClient.updateBuy(one.getCourseId());
            payService.updateOrderStatus(map);
            return Rsponse.ok().message("支付成功");
        }

        return Rsponse.ok().code(25000).message("支付中");
    }
}

