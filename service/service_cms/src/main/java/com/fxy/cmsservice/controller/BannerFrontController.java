package com.fxy.cmsservice.controller;


import com.fxy.cmsservice.entity.CrmBanner;
import com.fxy.cmsservice.service.CrmBannerService;
import com.fxy.commonutils.Rsponse;
import org.bouncycastle.pqc.crypto.rainbow.RainbowSigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-11-05
 */
@RestController
@RequestMapping("/cmsservice/bannerFront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;


    @GetMapping("getAllBanner")
    public Rsponse getAll(){

        List<CrmBanner> list = bannerService.selectAllBanner();
        return Rsponse.ok().data("list",list);
    }

}

