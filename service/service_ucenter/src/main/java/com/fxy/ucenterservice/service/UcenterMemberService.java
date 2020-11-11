package com.fxy.ucenterservice.service;

import com.fxy.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fxy.ucenterservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author fxy
 * @since 2020-11-06
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    Integer countRegister(String day);
}
