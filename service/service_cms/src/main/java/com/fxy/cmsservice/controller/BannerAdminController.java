package com.fxy.cmsservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.cmsservice.entity.CrmBanner;
import com.fxy.cmsservice.service.CrmBannerService;
import com.fxy.commonutils.Rsponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author fxy
 * @since 2020-11-05
 */
@RestController
@RequestMapping("/cmsservice/bannerAdmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @GetMapping(value = "pageBanner/{page}/{limit}")
    public  Rsponse pageBanner(@PathVariable long page, @PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        bannerService.page(pageBanner,null);

        return Rsponse.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public Rsponse get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return Rsponse.ok().data("item", banner);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("save")
    public Rsponse save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return Rsponse.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public Rsponse updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return Rsponse.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public Rsponse remove(@PathVariable String id) {
        bannerService.removeById(id);
        return Rsponse.ok();
    }

}

