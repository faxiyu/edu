package com.fxy.aclservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fxy.aclservice.entity.Role;
import com.fxy.aclservice.service.RolePermissionService;
import com.fxy.aclservice.service.RoleService;
import com.fxy.commonutils.Rsponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/role")
//@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService permissionService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("{page}/{limit}")
    public Rsponse index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            Role role) {
        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }
        roleService.page(pageParam,wrapper);
        return Rsponse.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("get/{id}")
    public Rsponse get(@PathVariable String id) {
        Role role = roleService.getById(id);
        return Rsponse.ok().data("item", role);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public Rsponse save(@RequestBody Role role) {
        roleService.save(role);
        return Rsponse.ok();
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public Rsponse updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return Rsponse.ok();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public Rsponse remove(@PathVariable String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("role_id",id);
        permissionService.remove(wrapper);
        roleService.removeById(id);
        return Rsponse.ok();
    }

    @ApiOperation(value = "根据id列表删除角色")
    @DeleteMapping("batchRemove")
    public Rsponse batchRemove(@RequestBody List<String> idList) {
        for (int i = 0; i < idList.size() ; i++) {
            String id = idList.get(i);
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("role_id",id);
            permissionService.remove(wrapper);
        }
        roleService.removeByIds(idList);
        return Rsponse.ok();
    }
}

