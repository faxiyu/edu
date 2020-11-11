package com.fxy.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fxy.commonutils.JwtUtils;
import com.fxy.commonutils.MD5;
import com.fxy.servicebase.exceptionHandler.FxyException;
import com.fxy.ucenterservice.entity.UcenterMember;
import com.fxy.ucenterservice.entity.vo.RegisterVo;
import com.fxy.ucenterservice.mapper.UcenterMemberMapper;
import com.fxy.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.geom.QuadCurve2D;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author fxy
 * @since 2020-11-06
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {
    @Autowired
    private RedisTemplate<String,String> template;
    @Override
    public String login(UcenterMember member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
            throw new FxyException(20001,"登陆失败");
        }
        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        if (ucenterMember==null){
            throw new FxyException(20001,"手机号不存在");
        }
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw  new FxyException(20001,"密码错误");
        }
        if (ucenterMember.getIsDisabled()){
            throw  new FxyException(20001,"该用户已被禁用");
        }

        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());
        return jwtToken;
    }

    @Override
    public void register(RegisterVo registerVo) {

        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();
        if(StringUtils.isEmpty(code)||StringUtils.isEmpty(password)||StringUtils.isEmpty(mobile)||StringUtils.isEmpty(nickname)){
            throw new FxyException(20001,"数据不能为空");
        }
        String redisCode = template.opsForValue().get(mobile);
        if (!code.equals(redisCode)){
            throw new FxyException(20001,"验证码错误");
        }
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer integer = baseMapper.selectCount(wrapper);
        if (integer>0){
            throw  new FxyException(20001,"手机号已存在");
        }

        QueryWrapper<UcenterMember> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("nickname",nickname);
        Integer integer1 = baseMapper.selectCount(wrapper1);
        if (integer1>0){
            throw  new FxyException(20001,"名称已存在");
        }

        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setPassword(MD5.encrypt(password));
        member.setMobile(mobile);
        member.setIsDisabled(false);
        member.setAvatar("https://chm-edu.oss-cn-beijing.aliyuncs.com/2020/10/31/a8467cfefc34467c976040909be6e776QQ%E5%9B%BE%E7%89%8720180126204705.jpg");
        baseMapper.insert(member);



    }

    @Override
    public Integer countRegister(String day) {
        return baseMapper.countRegister(day);
    }
}
