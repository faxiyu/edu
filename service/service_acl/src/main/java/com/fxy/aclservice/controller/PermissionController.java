package com.fxy.aclservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.fxy.aclservice.entity.Permission;
import com.fxy.aclservice.service.PermissionService;
import com.fxy.aclservice.service.RolePermissionService;
import com.fxy.commonutils.Rsponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
//@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    //获取全部菜单
    @ApiOperation(value = "查询所有菜单")
    @GetMapping
    public Rsponse indexAllPermission() {
        List<Permission> list =  permissionService.queryAllMenuGuli();
        return Rsponse.ok().data("children",list);
    }

    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("remove/{id}")
    public Rsponse remove(@PathVariable String id) {
        permissionService.removeChildByIdGuli(id);
        return Rsponse.ok();
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Rsponse doAssign(String roleId,String[] permissionId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("role_id",roleId);
        rolePermissionService.remove(wrapper);
        permissionService.saveRolePermissionRealtionShipGuli(roleId,permissionId);
        return Rsponse.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public Rsponse toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return Rsponse.ok().data("children", list);
    }



    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Rsponse save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Rsponse.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public Rsponse updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Rsponse.ok();
    }

}

