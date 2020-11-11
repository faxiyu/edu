package com.fxy.ucenterservice.mapper;

import com.fxy.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author fxy
 * @since 2020-11-06
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Integer countRegister(String day);
}
