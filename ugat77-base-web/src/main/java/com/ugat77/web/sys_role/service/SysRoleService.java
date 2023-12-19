package com.ugat77.web.sys_role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ugat77.web.sys_role.entity.RoleAssignParm;
import com.ugat77.web.sys_role.entity.RoleParm;
import com.ugat77.web.sys_role.entity.RolePermissionVo;
import com.ugat77.web.sys_role.entity.SysRole;

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> getList(RoleParm parm);

    //查询权限树回显
    RolePermissionVo getMenuTree(RoleAssignParm parm);

}
