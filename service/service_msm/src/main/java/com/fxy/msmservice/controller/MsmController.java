package com.fxy.msmservice.controller;

import com.fxy.commonutils.Rsponse;
import com.fxy.msmservice.service.MsmService;
import com.fxy.msmservice.utils.RandomUtil;
import org.bouncycastle.pqc.crypto.rainbow.RainbowSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("edumsm/msm")

public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信
    @GetMapping("send/{phone}")
    public Rsponse sendMsm(@PathVariable String phone){


        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return Rsponse.ok();
        }
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        boolean b =msmService.send(param,phone);
        if (b){
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            return Rsponse.ok();
        }else {
            return  Rsponse.error().message("短信发送失败");
        }
    }
}
