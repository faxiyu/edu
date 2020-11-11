package com.fxy.staservice.client;

import com.fxy.commonutils.Rsponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    @GetMapping("/ucenterservice/member/countRegister/{day}")
    public Rsponse countRegister(@PathVariable("day") String day);
}
