package com.fxy.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fxy.cmsservice.entity.CrmBanner;
import com.fxy.cmsservice.mapper.CrmBannerMapper;
import com.fxy.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author fxy
 * @since 2020-11-05
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(key = "'selectIndexList'",value = "banner")
    public List<CrmBanner> selectAllBanner() {
        QueryWrapper<CrmBanner> wrapper =new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> crmBanners = baseMapper.selectList(wrapper);
        return crmBanners;
    }
}
