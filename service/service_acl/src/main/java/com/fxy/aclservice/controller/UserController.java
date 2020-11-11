package com.fxy.aclservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.aclservice.entity.User;
import com.fxy.aclservice.service.RoleService;
import com.fxy.aclservice.service.UserService;
import com.fxy.commonutils.MD5;
import com.fxy.commonutils.Rsponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public Rsponse index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                    User userQueryVo) {
        Page<User> pageParam = new Page<>(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(userQueryVo.getUsername())) {
            wrapper.like("username",userQueryVo.getUsername());
        }

        IPage<User> pageModel = userService.page(pageParam, wrapper);
        return Rsponse.ok().data("items", pageModel.getRecords()).data("total", pageModel.getTotal());
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public Rsponse save(@RequestBody User user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return Rsponse.ok();
    }

    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public Rsponse updateById(@RequestBody User user) {
        userService.updateById(user);
        return Rsponse.ok();
    }

    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public Rsponse remove(@PathVariable String id) {
        userService.removeById(id);
        return Rsponse.ok();
    }

    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public Rsponse batchRemove(@RequestBody List<String> idList) {
        userService.removeByIds(idList);
        return Rsponse.ok();
    }

    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public Rsponse toAssign(@PathVariable String userId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return Rsponse.ok().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Rsponse doAssign(@RequestParam String userId,@RequestParam String[] roleId) {
        roleService.saveUserRoleRealtionShip(userId,roleId);
        return Rsponse.ok();
    }
}

