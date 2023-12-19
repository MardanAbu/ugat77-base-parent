package com.ugat77.web.sys_user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ugat77.web.sys_user.entity.PageParm;
import com.ugat77.web.sys_user.entity.SysUser;

public interface SysUserService extends IService<SysUser> {
    //Create
    void addUser(SysUser sysUser);

    //Edit
    void editUser(SysUser sysUser);

    //Delete
    void deleteUser(Long userId);

    //list
    IPage<SysUser> getList(PageParm parm);
}
