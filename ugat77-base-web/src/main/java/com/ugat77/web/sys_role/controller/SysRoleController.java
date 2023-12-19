package com.ugat77.web.sys_role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.sys_role.entity.*;
import com.ugat77.web.sys_role.service.SysRoleService;
import com.ugat77.web.sys_role_menu.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/role")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private RoleMenuService roleMenuService;

    //create role
    @PostMapping
    public ResultVo addRole(@RequestBody SysRole role){
        //set create time
        role.setCreateTime(new Date());
        if (sysRoleService.save(role)) {
            return ResultUtils.success("Creating new role succeed");
        }
        return ResultUtils.error("Creating new role failed!");
    }

    //Edit role
    @PutMapping
    public ResultVo editRole(@RequestBody SysRole role){
        //set update time
        role.setUpdateTime(new Date());
        boolean save = sysRoleService.updateById(role);
        if (save) {
            return ResultUtils.success("Updating new role succeed");
        }
        return ResultUtils.error("Updating new role failed!");
    }

    //Delete
    @DeleteMapping("/{roleId}")
    public ResultVo deleteRole(@PathVariable("roleId") Long roleId){
        if(sysRoleService.removeById(roleId)){
            return ResultUtils.success("Deleting succeed!");
        }
        return ResultUtils.error("Deleting failed!");
    }

    //list
    @GetMapping("/list")
    public ResultVo list(RoleParm parm){
        IPage<SysRole> list = sysRoleService.getList(parm);
        return ResultUtils.success("query succeed",list);
    }

    //new emp list
    @GetMapping("/getSelect")
    public ResultVo getListSelect() {
        List<SysRole> list = sysRoleService.list();
        List<SelectType> selectTypeList = new ArrayList<>();
        if (list.size() > 0){
            list.stream().forEach(item ->{
                SelectType selectType = new SelectType();
                selectType.setLabel(item.getRoleName());
                selectType.setValue(item.getRoleId());
                selectTypeList.add(selectType);
            });
        }
        return ResultUtils.success("Searching succeed", selectTypeList);
    }

    //分配权限树数据回显查询
    @GetMapping("/getMenuTree")
    public ResultVo getMenuTree(RoleAssignParm parm){
        RolePermissionVo tree = sysRoleService.getMenuTree(parm);
        return ResultUtils.success("Searching success",tree);
    }

    //save permission
    @PostMapping("/saveRoleMenu")
    public ResultVo saveRoleMenu(@RequestBody SaveMenuParm parm){
        roleMenuService.saveMenu(parm);
        return ResultUtils.success("Assigning success");
    }
}
