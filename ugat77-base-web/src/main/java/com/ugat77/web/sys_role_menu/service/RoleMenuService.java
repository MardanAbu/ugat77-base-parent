package com.ugat77.web.sys_role_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ugat77.web.sys_role.entity.SaveMenuParm;
import com.ugat77.web.sys_role_menu.entity.RoleMenu;

public interface RoleMenuService extends IService<RoleMenu> {
    //save permission
    void saveMenu(SaveMenuParm parm);
}
