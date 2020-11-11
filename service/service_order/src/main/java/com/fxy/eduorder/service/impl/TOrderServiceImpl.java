package com.fxy.eduorder.service.impl;

import com.fxy.commonutils.orderVo.CourseVoOrder;
import com.fxy.commonutils.orderVo.UcenterMemberOrder;
import com.fxy.eduorder.client.EduClient;
import com.fxy.eduorder.client.UcenterClient;
import com.fxy.eduorder.entity.TOrder;
import com.fxy.eduorder.mapper.TOrderMapper;
import com.fxy.eduorder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fxy.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author fxy
 * @since 2020-11-09
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public String saveOrder(String courseId, String memberIdByJwtToken) {
        UcenterMemberOrder userInfo = ucenterClient.getUserInfo(memberIdByJwtToken);
        CourseVoOrder courseInfo = eduClient.getCourseInfo(courseId);
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberIdByJwtToken);
        order.setMobile(userInfo.getMobile());
        order.setNickname(userInfo.getNickname());
        order.setStatus(0);
        order.setPayType(1);
        baseMapper.insert(order);
        return order.getOrderNo();
    }
}
