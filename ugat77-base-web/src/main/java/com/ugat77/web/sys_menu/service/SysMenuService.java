package com.ugat77.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ugat77.web.sys_menu.entity.SysMenu;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {
    List<SysMenu> getParent();

    //get menu by employee id
    List<SysMenu> getMenuByUserId(Long userId);
    //get menu by Member id
    List<SysMenu> getMenuByMemberId(Long userId);
    //get menu by role id
    List<SysMenu> getMenuByRoleId(Long roleId);
}
