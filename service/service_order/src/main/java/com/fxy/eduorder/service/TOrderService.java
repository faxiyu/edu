package com.fxy.eduorder.service;

import com.fxy.eduorder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author fxy
 * @since 2020-11-09
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String courseId, String memberIdByJwtToken);
}
