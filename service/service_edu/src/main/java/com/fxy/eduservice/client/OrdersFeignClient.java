package com.fxy.eduservice.client;

import com.fxy.servicebase.exceptionHandler.FxyException;
import org.springframework.stereotype.Component;

@Component
public class OrdersFeignClient implements OrdersClient {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        throw new FxyException(20001,"查询购买状态失败");
    }
}
