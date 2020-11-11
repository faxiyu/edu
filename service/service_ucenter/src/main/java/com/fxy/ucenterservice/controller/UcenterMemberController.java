package com.fxy.ucenterservice.controller;


import com.fxy.commonutils.JwtUtils;
import com.fxy.commonutils.Rsponse;
import com.fxy.commonutils.orderVo.UcenterMemberOrder;
import com.fxy.ucenterservice.entity.UcenterMember;
import com.fxy.ucenterservice.entity.vo.RegisterVo;
import com.fxy.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-11-06
 */
@RestController
@RequestMapping("/ucenterservice/member")

public class UcenterMemberController {
    @Autowired
    private UcenterMemberService memberService;

    @PostMapping("login")
    public Rsponse loginMember(@RequestBody UcenterMember member){
        String token = memberService.login(member);
        return Rsponse.ok().data("token",token);
    }

    @PostMapping("register")
    public Rsponse registerUser(@RequestBody RegisterVo registerVo){
        memberService.register(registerVo);
        return Rsponse.ok();
    }

    @GetMapping("getMemberInfo")
    public Rsponse getMemberInfo(HttpServletRequest request){

        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember byId = memberService.getById(memberId);
        return Rsponse.ok().data("userIInfo",byId);
    }

    @PostMapping("getUserInfo/{id}")
    public UcenterMemberOrder getUserInfo(@PathVariable String id){

        UcenterMember byId = memberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();

        BeanUtils.copyProperties(byId,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
    //查询某一天注册人数
    @GetMapping("countRegister/{day}")
    public Rsponse countRegister(@PathVariable String day){
        Integer count = memberService.countRegister(day);
        return Rsponse.ok().data("countRegister",count);
    }
}

