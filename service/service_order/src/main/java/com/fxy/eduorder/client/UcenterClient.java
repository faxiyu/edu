package com.fxy.eduorder.client;

import com.fxy.commonutils.orderVo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name="service-ucenter")
public interface UcenterClient {
    @PostMapping("/ucenterservice/member/getUserInfo/{id}")
    public UcenterMemberOrder getUserInfo(@PathVariable("id") String id);
}
