package com.fxy.cmsservice.service;

import com.fxy.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author fxy
 * @since 2020-11-05
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> selectAllBanner();
}
